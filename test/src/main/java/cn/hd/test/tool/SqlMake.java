package cn.hd.test.tool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SqlMake {
    public static void main(String[] args) throws Exception {
        String tableName = "examination_package";
        String tableComment = "体检机构对接商品套餐表";
        List<String> list = Files.readAllLines(Paths.get("test/" + tableName + ".txt"));
        String[] as;
        String kai = "CREATE TABLE `" + tableName + "` (";
        System.out.println(kai);
        String id = tableName.split("_", 2)[1] + "_id";
        String startId = "  `" + id + "` varchar(30) NOT NULL COMMENT '机构Id',";
        System.out.println(startId);
        String tmp;
        for (String s : list) {
            as = s.split("\t", 5);
            tmp = "  `" + as[0] +"` " + getType(as[1]) + " " + getDefault(as[2], as[1]) + " COMMENT '" + as[4] + "eg:" + as[3] + "',";

            System.out.println(tmp);
        }
        System.out.println("  PRIMARY KEY (`" + id + "`)");
        System.out.println(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='"+tableComment+"';");
    }

    private static String getDefault(String input, String type) {
        if ("true".equals(input)) {
            return "NOT NULL";
        }
        if ("String".equals(type)) {
            return "DEFAULT \"\"";
        } else {
            return "DEFAULT NULL";
        }
    }

    private static String getType(String input) {
        if ("String".equals(input)) {
            return "varchar(30)";
        } else if ("Number".equals(input)) {
            return "int(11)";
        }
        throw new RuntimeException("未定义字段类型:" + input);
    }

    private static String toTf(String input) {
        if (input.contains("_")) {
            StringBuilder sb = new StringBuilder();
            char [] x = input.toCharArray();
            for (int i = 0; i < x.length; ++i) {
                if (x[i] == '_') {
                    if (i+1 < x.length) {
                        x[i+1] = Character.toUpperCase(x[i+1]);
                    }
                    
                } else {
                    sb.append(x[i]);
                }

            }
            return sb.toString();
        }
        return input;
    }
}
