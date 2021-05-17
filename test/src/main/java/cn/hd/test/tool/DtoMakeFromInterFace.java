package cn.hd.test.tool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DtoMakeFromInterFace {
    public static void main(String[] args) throws Exception {
        List<String> list = Files.readAllLines(Paths.get("test/message.txt"));
        String field = "";
        String type = "";
        String required = "";
        String annotation = "";
        for (String s : list) {
            // 截取字段
            String [] arr = s.split("\t", 5);
            field = arr[0];
            // 转为驼峰
            char[] tr = field.toCharArray();
            StringBuilder sb = new StringBuilder();
            // 有bug
            for (int i = 0; i < tr.length; i++) {
                if (tr[i] == '_') {
                    tr[i + 1] = Character.toUpperCase(tr[i + 1]);
                } else {
                    sb.append(tr[i]);
                }
            }
            field = sb.toString();

            // 截取类型
            type = arr[1];

            // 截取注释
            annotation = arr[4] + "eg:" + arr[3];
            required = arr[2];
            System.out.println("@ApiModelProperty(name = \"" + field + "\", value = \"" + annotation + "\", required = " + required + ")");
            System.out.println("private " + type + " " + field + ";");
            System.out.println();

        }
    }
}
