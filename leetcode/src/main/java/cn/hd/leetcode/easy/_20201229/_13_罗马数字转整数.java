package cn.hd.leetcode.easy._20201229;

import java.util.HashMap;

public class _13_罗马数字转整数 {
    public static void main(String[] args) {
        _13_罗马数字转整数 _ = new _13_罗马数字转整数();
        String s = "DCXXI";
        System.out.println(_.romanToInt(s));
    }

    public int romanToInt(String s) {
        char [] sc = s.toCharArray();
        int r = 0;
        for (int i = 0; i < sc.length; i++) {
            if (sc[i] == 'I') {
                if (i + 1 < sc.length) {
                    if (sc[i+1] == 'V') {
                        r += getInt("IV");
                        i++;
                    } else if (sc[i+1] == 'X') {
                        r += getInt("IX");
                        i++;
                    } else {
                        r += getInt("I");
                    }
                } else {
                    r += getInt("I");
                }
            } else if (sc[i] == 'X') {
                if (i + 1 < sc.length) {
                    if (sc[i+1] == 'L') {
                        r += getInt("XL");
                        i++;
                    } else if (sc[i+1] == 'C') {
                        r += getInt("XC");
                        i++;
                    } else {
                        r += getInt("X");
                    }
                } else {
                    r += getInt("X");
                }
            } else if (sc[i] == 'C') {
                if (i + 1 < sc.length) {
                    if (sc[i+1] == 'D') {
                        r += getInt("CD");
                        i++;
                    } else if (sc[i+1] == 'M') {
                        r += getInt("CM");
                        i++;
                    } else {
                        r += getInt("C");
                    }
                } else {
                    r += getInt("C");
                }
            } else {
                r += getInt(String.valueOf(sc[i]));
            }
        }
        return r;
    }

    private int getInt(String lm) {
        if ("I".equals(lm)) {
            return 1;
        } else if ("V".equals(lm)) {
            return 5;
        } else if ("X".equals(lm)) {
            return 10;
        } else if ("L".equals(lm)) {
            return 50;
        } else if ("C".equals(lm)) {
            return 100;
        }else if ("D".equals(lm)) {
            return 500;
        }else if ("M".equals(lm)) {
            return 1000;
        }else if ("IV".equals(lm)) {
            return 4;
        }else if ("IX".equals(lm)) {
            return 9;
        }else if ("XL".equals(lm)) {
            return 40;
        }else if ("XC".equals(lm)) {
            return 90;
        }else if ("CD".equals(lm)) {
            return 400;
        }else if ("CM".equals(lm)) {
            return 900;
        }
        throw new RuntimeException("未定义");
    }
}
