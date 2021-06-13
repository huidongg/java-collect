package cn.hd.withjdk.polymorphism;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String x = "0000";
        System.out.println(isSmsCode(x));
    }

    public static boolean isSmsCode(String input) {
        String regex = "\\d{4}$";
        return match(regex, input);
    }

    public static boolean isPhoneNum(String input) {
        String regex = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
        return match(regex, input);
    }
    public static boolean match(String regex, String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        input = input.trim();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean result = matcher.matches();
        return result;
    }
}
