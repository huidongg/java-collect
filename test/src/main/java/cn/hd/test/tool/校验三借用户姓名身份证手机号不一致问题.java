package cn.hd.test.tool;

import cn.hd.test.utils.DESUtils;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class 校验三借用户姓名身份证手机号不一致问题 {
    private static String DESKEY = "432fe1dd3a82427b8e5a1d3e4a7f649B";
    private static Connection conn;

    public static void main(String[] args) {
        System.out.println(decode("c4nYZJ1GmPRYk2tNAt6HBA=="));
        System.out.println(decode("9yzpoHZmpZo6Cef1w7lfHchXbUmlX97V"));
        System.out.println(decode("nlBS9t5nOTkjv4LBDh/q+w=="));

        System.out.println(decode("cd/Jwj0f1/lByAuRLfv3mw=="));
        System.out.println(decode("rA27f7A6qKrvoTQmeAKkoqf0W8mcrGDA"));
        System.out.println(decode("dTGFcyj+WgN59cwDwe89tg=="));

    }

    public static void main1(String[] args) throws IOException, SQLException, ClassNotFoundException {
        getConn();
//        truncateTestTable();
        Path inputPath = Paths.get("test/yang_y.txt");
        List<String> userList = Files.readAllLines(inputPath);
        String[] tar;
        List<Sx> list = new ArrayList<>(1000);
        for (int i = 0; i < userList.size(); i++) {
            if (i % 1000 == 0) {
                System.out.println("检查第" + i + "行");
            }
            tar = userList.get(i).split("\t", 4);
            Long accountId = queryAccountIdByAssetId(tar[0]);
            int tableIdx = (int)(accountId % 20);
            Long personId = queryPersonIdByAccountId(accountId, tableIdx);
            String [] personAr = queryPersonByPersonId(personId, tableIdx);

            Sx sx = new Sx(tar[0], tar[1], tar[2], tar[3], personAr[0], personAr[1], personAr[2]);
            list.add(sx);
            if (list.size() == 1000) {
                batchInsert(list);
                list.clear();
            }
        }
        if (list.size() > 0) {
            batchInsert(list);
        }
    }

    private static Long queryAccountIdByAssetId(String assetId) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select account_id from fb_loan.loan where asset_id='" + assetId + "'");
        while (rs.next()) {
            return rs.getLong("account_id");
        }
        return null;
    }

    private static Long queryPersonIdByAccountId(Long accountId, int tableIdx) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select main_id from fb_account.account_" + tableIdx + " where account_id='" + accountId + "'");
        while (rs.next()) {
            return rs.getLong("main_id");
        }
        return null;
    }

    private static String [] queryPersonByPersonId(Long personId, int tableIdx) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select real_name, id_no, mobile from fb_account.person_" + tableIdx + " where person_id='" + personId + "'");
        String [] personAr = new String[3];
        while (rs.next()) {
            personAr[0] = decode(rs.getString("real_name"));
            personAr[1] = decode(rs.getString("id_no"));
            personAr[2] = decode(rs.getString("mobile"));
            return personAr;
        }
        return null;
    }

    private static void batchInsert(List<Sx> list) throws SQLException, ClassNotFoundException {
        String sql = " INSERT INTO test_data.compare_sanjie_xiaolian(asset_id, name_sanjie, id_no_sanjie, mobile_sanjie, name_xiaolian, id_no_xiaolian, mobile_xiaolian) VALUES  (?,?,?,?,?,?,?) ";
        Connection connectTest = getConnTest();
        connectTest.setAutoCommit(false);
        PreparedStatement stmt =  connectTest.prepareStatement(sql);
        for (Sx sx : list) {
            stmt.setString(1, sx.getAssetId());
            stmt.setString(2, sx.getNameSanjie());
            stmt.setString(3, sx.getIdNoSanjie());
            stmt.setString(4, sx.getMobileSanjie());
            stmt.setString(5, sx.getNameXiaolian());
            stmt.setString(6, sx.getIdNoXiaolian());
            stmt.setString(7, sx.getMobileXiaolian());
            stmt.addBatch();
        }
        System.out.println("before executing batch...");
        stmt.executeBatch();
        connectTest.commit();
        System.out.println("after batch executed!");
        connectTest.setAutoCommit(true);
        connectTest.close();
    }

    @Data
    @AllArgsConstructor
    static class Sx {
        private String assetId;
        private String nameSanjie;
        private String idNoSanjie;
        private String mobileSanjie;
        private String nameXiaolian;
        private String idNoXiaolian;
        private String mobileXiaolian;
    }

    private static String [] queryPersonByIdNo(String idNo) throws SQLException {
        String encodeIdNo = encode(idNo);
        Statement statement = conn.createStatement();
        String sql;
        String [] tar = null;
        B:
        for (int i = 0; i <= 10; i++) {
            sql = "select real_name, mobile from fb_account.person_" + i + " where id_no='" +  encodeIdNo +"'";
            ResultSet rs = statement.executeQuery(sql);
            tar = new String[2];
            while (rs.next()) {
                tar[0] = decode(rs.getString("real_name"));
                tar[1] = decode(rs.getString("mobile"));
                break B;
            }
            rs.close();
        }
        statement.close();
        return tar;
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

    private static void truncateTestTable() throws SQLException, ClassNotFoundException {
        Connection connection = getConnTest();
        Statement statement = conn.createStatement();
        statement.execute("truncate test_data.compare_sanjie_xiaolian");
        statement.close();
        connection.close();
    }

    private final static Connection getConnTest() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sqlUrl11 = "jdbc:mysql://10.1.1.14:33306/";
        String user11 = "root";
        String password11 = "root";
        return DriverManager.getConnection(sqlUrl11,user11,password11);
    }

    /**
     * 加密字符串
     * @param source
     * @return result
     */
    private static String encode(String source) {
        String result = "";
        try {
            byte[] data = DESUtils.encrypt(source.getBytes(), DESKEY.getBytes());
            result = Base64.encode(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密字符串
     * @param source
     * @return result
     */
    private static String decode(String source) {
        String result = "";
        try {
            byte[] data = Base64.decode(source);
            data = DESUtils.decrypt(data, DESKEY.getBytes());
            result = new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
