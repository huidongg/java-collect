package cn.hd.test.facebank;

import cn.hd.test.utils.DateUtils;
import cn.hd.test.utils.RestCustUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Flowing {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Integer RELEASE = 1;
    private static final Integer WITHDRAWAL = 2;

    // sql
    private static final String INSERT_FLOWING_SQL = "insert depository_flowing.flowing_202106 (asset_id, loan_id, account_no, request_no, request_type, flag) values(?,?,?,?,?,?)";
    private static final String QUERY_FLOWING_SQL = "select * from depository_flowing.flowing_202106 where asset_id=? and request_type=?";
    private static final String UPDATE_FLOWING_SQL = "update depository_flowing.flowing_202106 set flag =?, modify_time=? where flowing_id =?";
    private static final String QUERY_RELEASE_SQL = "select request_no, transaction_time from fb_release.depository_record where loan_id =? and back_status = 'SUCCESS'";
    private static final String QUERY_RELEASE_HISTORY_SQL = "select request_no, transaction_time from fb_release.depository_record_history where loan_id =? and back_status = 'SUCCESS'";
    private static final String QUERY_LOAN_SQL = "select asset_id, loan_id, account_no from fb_loan.loan where asset_id=?";
    private static final String QUERY_WITHDRAWAL_SQL = "SELECT b.third_trade_flow, b.update_time FROM fb_release.loan_withdrawal_record a LEFT JOIN fb_checkstand.withdraw_pay_record b on a.loan_withdrawal_req_no = b.apply_request_no where a.loan_id =? and a.withdrawal_status = 530";

    private static final String QUERY_FLOWING_FOR_PAGE_SQL = "select * from depository_flowing.flowing_202106 where request_type=? limit ?,?";


    // oss
    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static final String accessKeyId = "LTAIpNl9fWXPQeIO";
    private static final String accessKeySecret = "XCkd73thbqqLcpN0pSSoHarp6J1jXS";
    // 填写Bucket名称。
    private static final String bucketName = "depository-checkfile";
    private static final String releaseDirPath = "D:\\flowing\\release\\";
    private static final String withDrawalDirPath = "D:\\flowing\\withdrawal\\";

    private static Connection connPro;
    private static Connection connDev;

    private static OSS ossClient;

    public static void main(String[] args) throws IOException {
        init();
        downloadFlowing();
    }

    private static void downloadFlowing() {
        // 放款流水文件下载, 4个线程, 每个线程10000条
        long start = System.currentTimeMillis();
        new Thread("放款流水0->10000"){
            public void run() {
                List<FlowingModel> list = queryPageList(RELEASE, 0, 10000);
                int i = 0;
                for (FlowingModel flowingModel : list) {
                    System.out.println("当前放款流水线程[" + Thread.currentThread().getName() +"]处理第:" + i++ + "个资产id");
                    try {
                        downloadFlowing(flowingModel);
                        String requestNoEnd = flowingModel.getRequestNo().split("-", 3)[2];
                        String newFileName;
                        if (requestNoEnd.equals("0")) {
                            newFileName = flowingModel.getAssetId() + ".pdf";
                        }else  {
                            newFileName = flowingModel.getAssetId() + "_" + requestNoEnd + ".pdf";
                        }
                        downloadLocal(flowingModel, releaseDirPath, newFileName);
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f3.txt"),  (flowingModel.getFlowingId() + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前放款流水线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();
        new Thread("放款流水10000->20000"){
            public void run() {
                List<FlowingModel> list = queryPageList(RELEASE, 10000, 10000);
                int i = 0;
                for (FlowingModel flowingModel : list) {
                    System.out.println("当前放款流水线程[" + Thread.currentThread().getName() +"]处理第:" + i++ + "个资产id");
                    try {
                        downloadFlowing(flowingModel);
                        String requestNoEnd = flowingModel.getRequestNo().split("-", 3)[2];
                        String newFileName;
                        if (requestNoEnd.equals("0")) {
                            newFileName = flowingModel.getAssetId() + ".pdf";
                        }else  {
                            newFileName = flowingModel.getAssetId() + "_" + requestNoEnd + ".pdf";
                        }
                        downloadLocal(flowingModel, releaseDirPath, newFileName);
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f3.txt"),  (flowingModel.getFlowingId() + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前放款流水线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();
        new Thread("放款流水20000->30000"){
            public void run() {
                List<FlowingModel> list = queryPageList(RELEASE, 20000, 10000);
                int i = 0;
                for (FlowingModel flowingModel : list) {
                    System.out.println("当前放款流水线程[" + Thread.currentThread().getName() +"]处理第:" + i++ + "个资产id");
                    try {
                        downloadFlowing(flowingModel);
                        String requestNoEnd = flowingModel.getRequestNo().split("-", 3)[2];
                        String newFileName;
                        if (requestNoEnd.equals("0")) {
                            newFileName = flowingModel.getAssetId() + ".pdf";
                        }else  {
                            newFileName = flowingModel.getAssetId() + "_" + requestNoEnd + ".pdf";
                        }
                        downloadLocal(flowingModel, releaseDirPath, newFileName);
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f3.txt"),  (flowingModel.getFlowingId() + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前放款流水线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();
        new Thread("放款流水30000->结束"){
            public void run() {
                List<FlowingModel> list = queryPageList(RELEASE, 30000, 10000);
                int i = 0;
                for (FlowingModel flowingModel : list) {
                    System.out.println("当前放款流水线程[" + Thread.currentThread().getName() +"]处理第:" + i++ + "个资产id");
                    try {
                        downloadFlowing(flowingModel);
                        String requestNoEnd = flowingModel.getRequestNo().split("-", 3)[2];
                        String newFileName;
                        if (requestNoEnd.equals("0")) {
                            newFileName = flowingModel.getAssetId() + ".pdf";
                        }else  {
                            newFileName = flowingModel.getAssetId() + "_" + requestNoEnd + ".pdf";
                        }
                        downloadLocal(flowingModel, releaseDirPath, newFileName);
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f3.txt"),  (flowingModel.getFlowingId() + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前放款流水线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();

        // 提现流水文件下载, 4个线程, 每个线程10000条
        new Thread("提现流水0->10000"){
            public void run() {
                List<FlowingModel> list = queryPageList(WITHDRAWAL, 0, 10000);
                int i = 0;
                for (FlowingModel flowingModel : list) {
                    System.out.println("当前提现流水线程[" + Thread.currentThread().getName() +"]处理第:" + i++ + "个资产id");
                    try {
                        downloadFlowing(flowingModel);
                        downloadLocal(flowingModel, withDrawalDirPath, flowingModel.getAssetId() + ".pdf");
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f3.txt"),  (flowingModel.getFlowingId() + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前提现流水线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();
        new Thread("提现流水10000->20000"){
            public void run() {
                List<FlowingModel> list = queryPageList(WITHDRAWAL, 10000, 10000);
                int i = 0;
                for (FlowingModel flowingModel : list) {
                    System.out.println("当前提现流水线程[" + Thread.currentThread().getName() +"]处理第:" + i++ + "个资产id");
                    try {
                        downloadFlowing(flowingModel);
                        downloadLocal(flowingModel, withDrawalDirPath, flowingModel.getAssetId() + ".pdf");
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f3.txt"),  (flowingModel.getFlowingId() + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前提现流水线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();
        new Thread("提现流水20000->30000"){
            public void run() {
                List<FlowingModel> list = queryPageList(WITHDRAWAL, 20000, 10000);
                int i = 0;
                for (FlowingModel flowingModel : list) {
                    System.out.println("当前提现流水线程[" + Thread.currentThread().getName() +"]处理第:" + i++ + "个资产id");
                    try {
                        downloadFlowing(flowingModel);
                        downloadLocal(flowingModel, withDrawalDirPath, flowingModel.getAssetId() + ".pdf");
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f3.txt"),  (flowingModel.getFlowingId() + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前提现流水线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();
        new Thread("提现流水30000->结束"){
            public void run() {
                List<FlowingModel> list = queryPageList(WITHDRAWAL, 30000, 10000);
                int i = 0;
                for (FlowingModel flowingModel : list) {
                    System.out.println("当前提现流水线程[" + Thread.currentThread().getName() +"]处理第:" + i++ + "个资产id");
                    try {
                        downloadFlowing(flowingModel);
                        downloadLocal(flowingModel, withDrawalDirPath, flowingModel.getAssetId() + ".pdf");
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f3.txt"),  (flowingModel.getFlowingId() + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前提现流水线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();
    }

    private static void createFlowing() throws IOException {
        // 将文件中的assetId放到内存
        List<String> assetIdList = Files.readAllLines(Paths.get("test/f1.txt"));
        long start = System.currentTimeMillis();
        new Thread("0->10000"){
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    System.out.println("当前线程[" + Thread.currentThread().getName() +"]处理第:" + i + "个资产id");
                    try {
                        Loan loan = queryLoan(assetIdList.get(i));
                        // 创建放款流水
                        createReleaseFlowing(loan);
                        // 创建提现流水
                        createWithdrawalFlowing(loan);
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f2.txt"),  (assetIdList.get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();

        new Thread("10000->20000"){
            public void run() {
                for (int i = 10000; i < 20000; i++) {
                    System.out.println("当前线程[" + Thread.currentThread().getName() +"]处理第:" + i + "个资产id");
                    try {
                        Loan loan = queryLoan(assetIdList.get(i));
                        // 创建放款流水
                        createReleaseFlowing(loan);
                        // 创建提现流水
                        createWithdrawalFlowing(loan);
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f2.txt"),  (assetIdList.get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();

        new Thread("20000->30000"){
            public void run() {
                for (int i = 20000; i < 30000; i++) {
                    System.out.println("当前线程[" + Thread.currentThread().getName() +"]处理第:" + i + "个资产id");
                    try {
                        Loan loan = queryLoan(assetIdList.get(i));
                        // 创建放款流水
                        createReleaseFlowing(loan);
                        // 创建提现流水
                        createWithdrawalFlowing(loan);
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f2.txt"),  (assetIdList.get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000  + "s");
            }
        }.start();

        new Thread("30000->结束"){
            public void run() {
                for (int i = 30000; i < assetIdList.size(); i++) {
                    System.out.println("当前线程[" + Thread.currentThread().getName() +"]处理第:" + i + "个资产id");
                    try {
                        Loan loan = queryLoan(assetIdList.get(i));
                        // 创建放款流水
                        createReleaseFlowing(loan);
                        // 创建提现流水
                        createWithdrawalFlowing(loan);
                    } catch (Exception e) {
                        try {
                            Files.write(Paths.get("test/f2.txt"),  (assetIdList.get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                System.out.println("当前线程[" + Thread.currentThread().getName() +"]共计用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
        }.start();
    }

    private static void singleTest() throws Exception {
        init();
        String assetId = "050c325ef9e14a33898def561ed36536";
        Loan loan = queryLoan(assetId);
        List<FlowingModel> releaseFlowingList = createReleaseFlowing(loan);
        if (releaseFlowingList.size() == 1) {
            downloadFlowing(releaseFlowingList.get(0));
            downloadLocal(releaseFlowingList.get(0), releaseDirPath, loan.getAssetId() + ".pdf");
        } else {
            for (int i = 0; i < releaseFlowingList.size(); i++) {
                downloadFlowing(releaseFlowingList.get(i));
                downloadLocal(releaseFlowingList.get(i), releaseDirPath, loan.getAssetId() + "_" + i + ".pdf");
            }
        }
        FlowingModel withdrawalFlowing = createWithdrawalFlowing(loan);
        downloadFlowing(withdrawalFlowing);
        downloadLocal(withdrawalFlowing, withDrawalDirPath, loan.getAssetId() + ".pdf");
        closeConn();
    }

    private static void init() {
        // 数据库连接初始化
        getConnPro();
        getConnDev();
        // 创建文件夹
        File f = new File(releaseDirPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File(withDrawalDirPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        if (ossClient == null) {
            ossClient = getOssClient();
        }
    }

    private static void closeConn() {
        if (connDev != null) {
            try {
                connDev.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connPro != null) {
            try {
                connPro.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static OSS getOssClient() {
        // 创建OSSClient实例。
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 标的放款流水生成
     * @param loan
     */
    private static List<FlowingModel> createReleaseFlowing(Loan loan) throws Exception {
        List<Release> releaseList = queryReleaseRequestNoList(loan.getLoanId());
        if (releaseList.size() == 0) {
            System.out.println("放款记录未找到,loanId=" + loan.getLoanId());
        }
        List<FlowingModel> list = new ArrayList<>();
        for (Release release : releaseList) {
            CreateTransactionFlowingHisResponse response = createFlowingHis(loan.getAccountNo(), release.getRequestNo(), release.getTransactionTime());
            if ("SUCCESS".equals(response.getStatus()) && "CREATING".equals(response.getFileStatus())) {
                list.add(addFlowing(loan.getAssetId(), loan.getLoanId(), loan.getAccountNo(), release.getRequestNo(), RELEASE,1));
            }
            // 重复请求, 说明已经生成, 插入成功
            else if ("100013".equals(response.getErrorCode())) {
                list.add(addFlowing(loan.getAssetId(), loan.getLoanId(), loan.getAccountNo(), release.getRequestNo(), RELEASE,1));
            } else {
                list.add(addFlowing(loan.getAssetId(), loan.getLoanId(), loan.getAccountNo(), release.getRequestNo(), RELEASE,0));
            }
        }
        return list;
    }
    /**
     * 标的提现流水生成
     * @param loan
     */
    private static FlowingModel createWithdrawalFlowing(Loan loan) throws SQLException {
        List<Release> releaseList = queryWithdrawalRequestNoList(loan.getLoanId());
        if (releaseList.size() == 0) {
            System.out.println("提现记录未找到,loanId=" + loan.getLoanId());
        }
        for (Release release : releaseList) {
            CreateTransactionFlowingHisResponse response = createFlowingHis(loan.getAccountNo(), release.getRequestNo(), release.getTransactionTime());
            if ("SUCCESS".equals(response.getStatus()) && "CREATING".equals(response.getFileStatus())) {
                return addFlowing(loan.getAssetId(), loan.getLoanId(), loan.getAccountNo(), release.getRequestNo(), WITHDRAWAL,1);
            } // 重复请求, 说明已经生成, 插入成功
            else if ("100013".equals(response.getErrorCode())) {
                return addFlowing(loan.getAssetId(), loan.getLoanId(), loan.getAccountNo(), release.getRequestNo(), WITHDRAWAL,1);
            } else {
                return addFlowing(loan.getAssetId(), loan.getLoanId(), loan.getAccountNo(), release.getRequestNo(), WITHDRAWAL,0);
            }
        }
        return null;
    }

    /**
     * 标的放款流水下载
     * @param assetId
     */
    private static void downloadFlowing(String assetId, Integer requestType) throws Exception {
        List<FlowingModel> list = queryList(assetId, requestType);
        for ( FlowingModel flowingModel : list) {
            downloadFlowing(flowingModel);
        }
    }

    private static void downloadFlowing(FlowingModel flowingModel) {
        if (flowingModel.getFlag() == 1) {
            try {
                downloadTransactionFlowing(flowingModel.getRequestNo());
                modifyReleaseFlowingFlag(flowingModel.getFlowingId(), 2);
                flowingModel.setFlag(2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void downloadLocal(FlowingModel flowingModel, String path, String newFileName) {
        if (flowingModel.getFlag() == 2) {
            try {
                ossClient.getObject(new GetObjectRequest(bucketName, "checkfiles/TransactionFlowing/" +flowingModel.getRequestNo() + ".zip"), new File(path + flowingModel.getRequestNo() + ".zip"));
                String outPath = unzip(path + flowingModel.getRequestNo() + ".zip", path);
                rename(outPath, path + newFileName);
                Files.delete(Paths.get(path + flowingModel.getRequestNo() + ".zip"));
                modifyReleaseFlowingFlag(flowingModel.getFlowingId(), 3);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

//    /**
//     * 标的放款流水下载
//     * @param assetId
//     */
//    private static void downloadReleaseToLocal(String assetId) throws SQLException, IOException {
//        if (ossClient == null) {
//            ossClient = getOssClient();
//        }
//        List<FlowingModel> list = queryList(assetId, RELEASE);
//        int i = 0;
//        for (FlowingModel flowingModel : list) {
//            downloadLocal(flowingModel, releaseDirPath);
//        }
//    }
//
//    /**
//     * 标的提现流水下载
//     * @param assetId
//     */
//    private static void downloadWithdrawalToLocal(String assetId) throws SQLException, IOException {
//        if (ossClient == null) {
//            ossClient = getOssClient();
//        }
//        List<FlowingModel> list = queryList(assetId, WITHDRAWAL);
//        int i = 0;
//        for (FlowingModel flowingModel : list) {
//            downloadLocal(flowingModel, withDrawalDirPath);
//        }
//    }

    private static FlowingModel addFlowing(String assetId, Long loanId, String accountNo, String requestNo, Integer requestType, Integer flag) throws SQLException {
        PreparedStatement ps = connDev.prepareStatement(INSERT_FLOWING_SQL, new String[]{"flowing_id"});
        ps.setString(1, assetId);
        ps.setLong(2, loanId);
        ps.setString(3, accountNo);
        ps.setString(4, requestNo);
        ps.setInt(5, requestType);
        ps.setInt(6, flag);
        FlowingModel flowingModel = null;
        if (ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            Integer flowingId = null;
            while(rs.next()){
                flowingId = rs.getInt(1);
            }
            rs.close();
            if (flowingId != null) {
                flowingModel = new FlowingModel();
                flowingModel.setFlowingId(flowingId);
                flowingModel.setAssetId(assetId);
                flowingModel.setLoanId(loanId);
                flowingModel.setAccountNo(accountNo);
                flowingModel.setRequestNo(requestNo);
                flowingModel.setRequestType(requestType);
                flowingModel.setFlag(flag);
            }
        }
        ps.close();
        return flowingModel;
    }

    private static List<FlowingModel> queryPageList(Integer requestType, Integer offset, Integer pageSize) {
        try {
            List<FlowingModel> list = new ArrayList<>();
            PreparedStatement ps = connDev.prepareStatement(QUERY_FLOWING_FOR_PAGE_SQL);
            ps.setInt(1, requestType);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FlowingModel flowingModel = new FlowingModel();
                list.add(flowingModel);
                flowingModel.setFlowingId(rs.getInt("flowing_id"));
                flowingModel.setAssetId(rs.getString("asset_id"));
                flowingModel.setLoanId(rs.getLong("loan_id"));
                flowingModel.setAccountNo(rs.getString("account_no"));
                flowingModel.setRequestNo(rs.getString("request_no"));
                flowingModel.setRequestType(rs.getInt("request_type"));
                flowingModel.setFlag(rs.getInt("flag"));
                flowingModel.setCreateTime(rs.getDate("create_time"));
                flowingModel.setModifyTime(rs.getDate("modify_time"));
            }
            rs.close();
            ps.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<FlowingModel> queryList(String assetId, Integer requestType) throws SQLException {
        List<FlowingModel> list = new ArrayList<>();
        PreparedStatement ps = connDev.prepareStatement(QUERY_FLOWING_SQL);
        ps.setString(1, assetId);
        ps.setInt(2, requestType);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            FlowingModel flowingModel = new FlowingModel();
            list.add(flowingModel);
            flowingModel.setFlowingId(rs.getInt("flowing_id"));
            flowingModel.setAssetId(rs.getString("asset_id"));
            flowingModel.setLoanId(rs.getLong("loan_id"));
            flowingModel.setAccountNo(rs.getString("account_no"));
            flowingModel.setRequestNo(rs.getString("request_no"));
            flowingModel.setRequestType(rs.getInt("request_type"));
            flowingModel.setFlag(rs.getInt("flag"));
            flowingModel.setCreateTime(rs.getDate("create_time"));
            flowingModel.setModifyTime(rs.getDate("modify_time"));
        }
        rs.close();
        ps.close();
        return list;
    }

    private static void modifyReleaseFlowingFlag(Integer flowingId, Integer flag) throws SQLException {
        PreparedStatement ps = connDev.prepareStatement(UPDATE_FLOWING_SQL);
        ps.setInt(1, flag);
        ps.setDate(2, new Date(System.currentTimeMillis()));
        ps.setInt(3, flowingId);
        int rs = ps.executeUpdate();
        System.out.println(rs);
        ps.close();
    }

    @Data
    private static class FlowingModel {
        private Integer flowingId;
        private String assetId;
        private Long loanId;
        private String accountNo;
        private String requestNo;
        private Integer requestType;
        private Integer flag;
        private Date createTime;
        private Date modifyTime;
    }

    private static List<Release> queryReleaseRequestNoList(Long loanId) throws SQLException {
        List<Release> list = new ArrayList<>();
        PreparedStatement ps = connPro.prepareStatement(QUERY_RELEASE_SQL);
        ps.setLong(1, loanId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Release release = new Release();
            release.setRequestNo(rs.getString("request_no"));
            release.setTransactionTime(rs.getLong("transaction_time"));
            list.add(release);
        }
        rs.close();
        ps.close();
        if (list.size() == 0) {
            ps = connPro.prepareStatement(QUERY_RELEASE_HISTORY_SQL);
            ps.setLong(1, loanId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Release release = new Release();
                release.setRequestNo(rs.getString("request_no"));
                release.setTransactionTime(rs.getLong("transaction_time"));
                list.add(release);
            }
        }
        rs.close();
        ps.close();
        return list;
    }

    @Data
    private static class Release {
        private String requestNo;
        private Long transactionTime;
    }

    private static List<Release> queryWithdrawalRequestNoList(Long loanId) throws SQLException {
        List<Release> list = new ArrayList<>();
        PreparedStatement ps = connPro.prepareStatement(QUERY_WITHDRAWAL_SQL);
        ps.setLong(1, loanId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Release release = new Release();
            release.setRequestNo(rs.getString("third_trade_flow"));
            release.setTransactionTime(rs.getLong("update_time"));
            list.add(release);
        }
        rs.close();
        ps.close();
        return list;
    }


    private static Loan queryLoan(String assetId) throws SQLException {
        Loan loan = new Loan();
        PreparedStatement ps = connPro.prepareStatement(QUERY_LOAN_SQL);
        ps.setString(1, assetId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            loan.setAssetId(rs.getString("asset_id"));
            loan.setLoanId(rs.getLong("loan_id"));
            loan.setAccountNo(rs.getString("account_no"));
        }
        rs.close();
        ps.close();
        return loan;
    }

    @Data
    private static class Loan {
        private String assetId;
        private Long loanId;
        private String accountNo;
    }

    private static void getConnPro()  {
        if (connPro == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String sqlUrl11 = "jdbc:mysql://olap-cgr.mysql.rds.aliyuncs.com:3306/";
                String user11 = "worker";
                String password11 = "3p]7r;eKaA7t{";
                connPro = DriverManager.getConnection(sqlUrl11,user11,password11);
            } catch (ClassNotFoundException |SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void getConnDev() {
        if (connDev == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String sqlUrl11 = "jdbc:mysql://10.1.1.14:33306/";
                String user11 = "root";
                String password11 = "root";
                connDev = DriverManager.getConnection(sqlUrl11,user11,password11);
            } catch (ClassNotFoundException |SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 不同的磁盘格式化类型可能会失败
     * @param oldNamePath
     * @param newNamePath
     */
    public static void rename(String oldNamePath, String newNamePath) {
        File oldf = new File(oldNamePath);
        boolean b = oldf.renameTo(new File(newNamePath));
        if (!b) {
            throw new RuntimeException("文件重命名失败");
        }
    }

    /**
     * 解压zip文件
     * @param zippath zip文件全路径
     * @param resourcepath zip文件所在的文件夹路径
     */
    public static String unzip(String zippath,String resourcepath){
        //判断生成目录是否生成，如果没有就创建
        File pathFile=new File(resourcepath);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zp=null;
        String outpath =null;
        try{
            //指定编码，否则压缩包里面不能有中文目录
            zp=new ZipFile(zippath, Charset.forName("gbk"));
            //遍历里面的文件及文件夹
            Enumeration entries=zp.entries();
            while(entries.hasMoreElements()){
                ZipEntry entry= (ZipEntry) entries.nextElement();
                String zipEntryName=entry.getName();
                InputStream in=zp.getInputStream(entry);
                outpath=(resourcepath+zipEntryName).replace("/",File.separator);
                //判断路径是否存在，不存在则创建文件路径
                File file = new File(outpath.substring(0,outpath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是,不需要解压
                if(new File(outpath).isDirectory())
                    continue;
                OutputStream out=new FileOutputStream(outpath);
                byte[] bf=new byte[2048];
                int len;
                while ((len=in.read(bf))>0){
                    out.write(bf,0,len);
                }
                in.close();
                out.close();
            }
            zp.close();
            return outpath;
        }catch ( Exception e){
            e.printStackTrace();
        }
        return outpath;
    }

    /**
     * 创建流水归档
     */
    private static final String FLOWING_HIS = "http://10.163.1.21:12000/v1.0/depositoryProxyQuery/createTransactionFlowingHis";
    private static final String DOWNLOAD_FLOWING = "http://10.163.1.21:12000/v1.0/depositoryProxyTrade/downloadTransactionFlowing";

    /**
     * 330624040453701632-1-0
     * @param accountNo 借款人存管号
     * @param requestNo 放款请求号
     * @param transationTime 开始时间为交易时间-1小时, 结束时间为交易时间+1小时
     */
    private static CreateTransactionFlowingHisResponse createFlowingHis(String accountNo, String requestNo, Long transationTime) {
        CreateTransactionFlowingHisRequest request = new CreateTransactionFlowingHisRequest();
        request.setBeginTime(DateUtils.format(transationTime - 3600000, DateUtils.FORMAT_DATA_ONLYNUM));
        request.setEndTime(DateUtils.format(transationTime + 3600000, DateUtils.FORMAT_DATA_ONLYNUM));
        request.setRequestNo(requestNo);
        request.setPlatformUserNo(accountNo);
        request.setUserDevice("PC");
        String [] requestNoArray = {requestNo};
        request.setRequestNoList(requestNoArray);
        RestModel restModel = restTemplate.postForObject(FLOWING_HIS, request, RestModel.class);
        System.out.println(JSON.toJSONString(restModel));
        return RestCustUtils.getModel(restModel, CreateTransactionFlowingHisResponse.class);
        // Files.write(Paths.get(dirPath + "createFlowing.txt"), JSON.toJSONString(restModel).getBytes(), StandardOpenOption.APPEND);
        // 入库
    }

    private static void downloadTransactionFlowing(String requestNo) {
        DownloadTransactionFlowingRequest request = new DownloadTransactionFlowingRequest();
        request.setRequestNo(requestNo);
        RestModel restModel = restTemplate.postForObject(DOWNLOAD_FLOWING, request, RestModel.class);
        System.out.println(JSON.toJSONString(restModel));
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class CreateTransactionFlowingHisRequest extends BaseRequest {
        private String requestNo;// Y S 50 请求流水号
        private String platformUserNo;// Y S 50 平台用户编号
        private String beginTime;// Y	D		开始日期，使用YYYYMMDD（如20141213）的格式。时区采用北京时间（GMT+8:00）
        private String endTime;// Y	D		结束日期，使用YYYYMMDD（如20141213）的格式。时区采用北京时间（GMT+8:00）
        private String[] requestNoList;// Y	C		复杂类型，数组形式}
    }

    @Data
    public static class BaseRequest {
        private String timestamp;// Y T 时间戳
        private String userDevice;//用户终端设备类型，见枚举【终端类型】，如果该参数为空，则系统会根据请求 head中 userAgent 信息判断是 PC 还是移动设备
        private String systemName;//系统名
        private String redirectUrl;// Y S 100 页面回跳 URL
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DownloadTransactionFlowingRequest extends BaseRequest {
        private String requestNo;// Y	S	50	请求流水号（文件请求生成的流水号
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class CreateTransactionFlowingHisResponse extends BaseResponse {
        private String requestNo;// Y SS 批次号
        private String fileStatus;// Y	E 见“文件生成状态”
    }

    @Data
    public static class BaseResponse {
        private String code;// Y E 调用状态（0 为调用成功、 1 为失败，返回 1 时请看【调用失败错误码】及错误码描述）
        private String status;// Y E 业务处理状态（处理失败 INIT；处理成功 SUCCESS），平台可根据非 SUCCESS状态做相应处理，处理失败时可参考错误码及描述
        private String errorCode;// N S 50 错误码
        private String errorMessage;// N S 50 错误码描述
    }

    @Data
    public static class RestModel {
        public static final String CODE_SUCCESS = "200";
        public static final String MESSAGE_SUCCESS = "成功";
        /**
         * 返回代码
         */
        private String code;
        /**
         * 返回的内容
         */
        private String message;
        /**
         * 返回附带的实体内容
         */
        private Object data;
    }
}
