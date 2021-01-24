package cn.hd.test.tool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据表字段生成ApiModelProperty
 */
public class DtoMake {
    public static void main(String[] args) throws Exception {
        List<String> list = Files.readAllLines(Paths.get("test/sql.txt"));
        String field = "";
        String type = "";;
        String annotation = "";;
        for (String s : list) {
            if (s.contains("NULL")) {
                // 截取字段
                Pattern r1 = Pattern.compile("(`)(.*?)(`)");
                Matcher m1 = r1.matcher(s);
                if (m1.find()) {
                    field = m1.group(2);
                    // 转为驼峰
                    char[] tr = field.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    // 有bug
                    for (int i = 0; i < tr.length; i++) {
                        if (tr[i] == '_') {
                            tr[i+1] = Character.toUpperCase(tr[i+1]);
                        } else {
                            sb.append(tr[i]);
                        }
                    }
                    field = sb.toString();
                }

                // 截取类型
                Pattern r2 = Pattern.compile("(` )(.*?)( NULL)");
                Matcher m2 = r2.matcher(s);
                if (m2.find()) {
                    type = m2.group(2);
                }
                if (type.contains("char") || type.contains("text") || type.contains("decimal")) {
                    type = "String";
                } else if (type.contains("datetime")) {
                    type = "Date";
                } else if (type.contains("bigint")) {
                    type = "Long";
                } else if (type.contains("int")) {
                    type = "Integer";
                }
                
                // 截取注释
                Pattern r3 = Pattern.compile("(COMMENT ')(.*?)(',)");
                Matcher m3 = r3.matcher(s);
                if (m3.find()) {
                    annotation = m3.group(2);
                }

                System.out.println("@ApiModelProperty(name = \""+ field +"\", value = \"" + annotation + "\", required = true)");
                System.out.println("private " + type + " " + field + ";");
                System.out.println();
            }
        }
    }
}
