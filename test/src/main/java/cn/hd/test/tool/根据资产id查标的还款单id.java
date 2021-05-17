package cn.hd.test.tool;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class 根据资产id查标的还款单id {
    static Connection conn;
    static Connection connHistory;


    public static void main(String[] args) throws SQLException, IOException {
        getConn();
        getConnHistory();
        //List<String> assetIdList = Files.readAllLines(Paths.get("test/message.txt"));
        List<String> assetIdList = new ArrayList<>(
                Arrays.asList(
                        "610102018081101429",
                        "610102018100502901"
                )
        );
//        assetIdList.add("1021201908292005125695473");
        for (String assetId : assetIdList) {
            yunsuan(assetId);
        }

    }

    private static void yunsuan(String assetId) throws SQLException, IOException{
        Long loanId = queryLoan(assetId);
        System.out.println("loan_id=" + loanId);
        int ct = queryTermCount(loanId);
        System.out.println("总条数:" + ct);
        List<LoanTerm> loanRepaymentIdList = queryLoanRepaymentRecord(loanId);
        if (loanRepaymentIdList.size() != ct) {
            // 查这个库的历史表
            queryLoanRepaymentRecordHistory(loanId, loanRepaymentIdList);
        }
        if (loanRepaymentIdList.size() != ct) {
            // 查历史库
            queryLoanRepaymentRecord202006(loanId, loanRepaymentIdList);
        }
        if (loanRepaymentIdList.size() != ct) {
            // 查历史库
            queryLoanRepaymentRecordHistoryHistory(loanId, loanRepaymentIdList);
        }
        if (loanRepaymentIdList.size() != ct) {
            System.out.println("*********************************************** " + assetId + "*********************************************** ");
            return;
        }
        loanRepaymentIdList.sort((x, y)->x.getCurrTermNum().compareTo(y.currTermNum));
//        System.out.println("标的还款单表数据:" + loanRepaymentIdList);
        log.info("标的还款单表数据loanRepaymentIdList={}",loanRepaymentIdList);

        List<Contract> list = queryContract(loanRepaymentIdList.stream().map(x->x.getLoanRepaymentId().toString()).collect(Collectors.toList()));
//        System.out.println(list);
        log.info("assid={},contract={}",assetId ,JSON.toJSONString(list));
//         Files.write(Paths.get("D:/java_wp/spb/src/main/resources/upload/f1.txt"), list.stream().map(x->x.getRelationId().toString()).collect(Collectors.toList()), StandardOpenOption.APPEND);
        String fileName = "test/f1.txt";
//         Files.write(Paths.get(file), list.stream().map(x->x.getRelationId().toString()).collect(Collectors.toList()), StandardOpenOption.APPEND);

        Path path = Paths.get(fileName);
        //追加写模式
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path,
                             StandardCharsets.UTF_8,
                             StandardOpenOption.APPEND)){
            for(Contract contract : list){
                writer.write(assetId+" "+ contract.getRelationId()+" "+contract.getSignPdfUrl());
                writer.newLine();
            }
        }

    }

    private static List<Contract> queryContract(List<String> loanRepaymentIdList) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = null;
        String relationIdStr = loanRepaymentIdList.stream().collect(Collectors.joining(","));
        rs = statement.executeQuery("SELECT relation_id,sign_pdf_url FROM fb_contract.contract_pdf_2019 where relation_id in(" + relationIdStr + ") and model_id=10071 and is_filing=1 and is_delete=0 UNION SELECT relation_id,sign_pdf_url FROM fb_contract.contract_pdf where relation_id in(" + relationIdStr + ") and model_id=10071 and is_filing=1 and is_delete=0");
        List<Contract> list = new ArrayList<>();
        while (rs.next()) {
            Contract contract = new Contract();
            contract.setRelationId(Long.valueOf(rs.getObject("relation_id").toString()));
            contract.setSignPdfUrl(rs.getObject("sign_pdf_url").toString());
            list.add(contract);
        }
        return list;
    }

    @Data
    private static class Contract {
        private Long relationId;
        private String signPdfUrl;
    }

    private static List<LoanTerm> queryLoanRepaymentRecordHistoryHistory(Long loanId, List<LoanTerm> list) throws SQLException {
        Statement statement = connHistory.createStatement();
        ResultSet rs = null;
        rs = statement.executeQuery("select loan_repayment_id, curr_term_num from fb_repayment.loan_repayment_record_history where loan_id=" + loanId + " and repayment_status = 600");
        while (rs.next()) {
            LoanTerm loanTerm = new LoanTerm();
            loanTerm.setLoanRepaymentId(Long.valueOf(rs.getObject("loan_repayment_id").toString()));
            loanTerm.setCurrTermNum(Integer.valueOf(rs.getObject("curr_term_num").toString()));
            list.add(loanTerm);
        }
        return list;
    }

    private static List<LoanTerm> queryLoanRepaymentRecord202006(Long loanId, List<LoanTerm> list) throws SQLException {
        Statement statement = connHistory.createStatement();
        ResultSet rs = null;
        rs = statement.executeQuery("select loan_repayment_id, curr_term_num from fb_repayment.loan_repayment_record_202006 where loan_id=" + loanId + " and repayment_status = 600");
        while (rs.next()) {
            LoanTerm loanTerm = new LoanTerm();
            loanTerm.setLoanRepaymentId(Long.valueOf(rs.getObject("loan_repayment_id").toString()));
            loanTerm.setCurrTermNum(Integer.valueOf(rs.getObject("curr_term_num").toString()));
            list.add(loanTerm);
        }
        return list;
    }

    private static List<LoanTerm> queryLoanRepaymentRecordHistory(Long loanId, List<LoanTerm> list) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = null;
        rs = statement.executeQuery("select loan_repayment_id, curr_term_num from fb_repayment.loan_repayment_record_history where loan_id=" + loanId + " and repayment_status = 600");
        while (rs.next()) {
            LoanTerm loanTerm = new LoanTerm();
            loanTerm.setLoanRepaymentId(Long.valueOf(rs.getObject("loan_repayment_id").toString()));
            loanTerm.setCurrTermNum(Integer.valueOf(rs.getObject("curr_term_num").toString()));
            list.add(loanTerm);
        }
        return list;
    }

    private static List<LoanTerm> queryLoanRepaymentRecord(Long loanId) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = null;
        rs = statement.executeQuery("select loan_repayment_id, curr_term_num from fb_repayment.loan_repayment_record where loan_id=" + loanId + " and repayment_status = 600");
        List<LoanTerm> rt = new ArrayList<>();
        while (rs.next()) {
            LoanTerm loanTerm = new LoanTerm();
            loanTerm.setLoanRepaymentId(Long.valueOf(rs.getObject("loan_repayment_id").toString()));
            loanTerm.setCurrTermNum(Integer.valueOf(rs.getObject("curr_term_num").toString()));
            rt.add(loanTerm);
        }
        return rt;
    }

    @Data
    private static class LoanTerm {
        private Long loanRepaymentId;
        private Integer currTermNum;
    }

    private static int queryTermCount(Long loanId) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = null;
        int f = 0;
        long b = loanId % 11;
        rs = statement.executeQuery("select count(*) as ct from fb_loan.loan_repayment_" + b + " where loan_id=" + loanId);
        while (rs.next()) {
            return Integer.valueOf(rs.getObject("ct").toString());
        }
        return 0;
    }

    private static Long queryLoan(String assetId) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = null;
        int f = 0;
        rs = statement.executeQuery("select loan_id from fb_loan.loan where asset_id='" + assetId + "'");
        while (rs.next()) {
            return Long.valueOf(rs.getObject("loan_id").toString());
        }
        return null;
    }

    private final static void getConn() throws SQLException {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String sqlUrl11 = "jdbc:mysql://olap-cgr.mysql.rds.aliyuncs.com:3306/";
                String user11 = "worker";
                String password11 = "3p]7r;eKaA7t{";
                conn = DriverManager.getConnection(sqlUrl11,user11,password11);
            } catch (ClassNotFoundException |SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final static void getConnHistory() throws SQLException {
        if (connHistory == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String sqlUrl11 = "jdbc:mysql://10.1.1.100:3306/?useSSL=true";
                String user11 = "history";
                String password11 = "facebank";
                connHistory = DriverManager.getConnection(sqlUrl11,user11,password11);
            } catch (ClassNotFoundException |SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
