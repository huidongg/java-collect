package cn.hd.test.tool;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class 测试经纬度计算速度 {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        List<City> list = selectCityList();

        Long c = System.currentTimeMillis();
        BigDecimal minjp;
        String minjName;
        City one = list.get(0);
        minjName = one.getName();
        minjp = jlpf(one.getLongitude(), one.getLatitude());
        for (int i = 1; i < list.size(); i++) {
            BigDecimal tmp = jlpf(list.get(i).getLongitude(), list.get(i).getLatitude());
            if (tmp.compareTo(minjp) < 0) {
                minjp = tmp;
                minjName = list.get(i).getName();
            }
        }
        System.out.println(minjName + ", 用时" + (System.currentTimeMillis() - c));
    }


    private static BigDecimal jlpf(BigDecimal x1, BigDecimal y1) {
//        BigDecimal x = new BigDecimal("116.488739");
//        BigDecimal y = new BigDecimal("39.91347");
        BigDecimal x = new BigDecimal("113.7");
        BigDecimal y = new BigDecimal("37.62");
        return x1.subtract(x).pow(2).add(y1.subtract(y).pow(2));
    }


    private static List<City> selectCityList() throws SQLException, ClassNotFoundException {
        Connection connection = getConnTest();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select name, longitude, latitude from xiquan_0526.city");
        List<City> list = new ArrayList<>();
        while (rs.next()) {
            City city = new City();
            city.setName(rs.getString("name"));
            city.setLongitude(rs.getBigDecimal("longitude"));
            city.setLatitude(rs.getBigDecimal("latitude"));
            list.add(city);
        }
        return list;
    }

    private final static Connection getConnTest() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sqlUrl11 = "jdbc:mysql://10.1.1.14:33306/";
        String user11 = "root";
        String password11 = "root";
        return DriverManager.getConnection(sqlUrl11,user11,password11);
    }

    @Data
    static class City {
        private String name;
        private BigDecimal longitude;
        private BigDecimal latitude;
    }
}
