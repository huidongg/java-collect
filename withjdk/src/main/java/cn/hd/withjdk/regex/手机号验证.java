package cn.hd.withjdk.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 手机号验证 {
    public static void main(String[] args) {
        simple("1352241867");
    }

    private static void simple(String mobile) {
        Pattern p=Pattern.compile("^1[0-9]{10}$");
        Matcher m=p.matcher(mobile);
        System.out.println(m.matches());
    }
}
