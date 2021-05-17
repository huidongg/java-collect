package cn.hd.test.file;

import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UrlFileTest1 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("105K");
        list.add("4M");
        list.add("616");
        for (String s : list) {
            System.out.println(toM(s));
        }
    }


    private static Float toM(String v) {
        String s = v.trim();
        Integer n = Integer.valueOf(s.replaceAll("[a-zA-Z]", ""));
        String m =  s.replaceAll("[0-9]", "");
        DecimalFormat decimalFormat=new DecimalFormat(".00");
        if ("K".equals(m) || "k".equals(m)) {
            return new Float(decimalFormat.format(n.floatValue()/1024));
        } else if ("M".equals(m) || "m".equals(m)) {
            return new Float(n);
        } else {
            if (StringUtils.isEmpty(m)) {
                return new Float(decimalFormat.format(n.floatValue()/1024/1024));
            } else {
                throw new RuntimeException("未定义类型");
            }
        }
    }
}
