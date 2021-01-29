package cn.hd.leetcode.exmple._2101;

public class _5661 {
    public static void main(String[] args) {
        _5661 _ = new _5661();
        String time = "??:??";
        System.out.println(_.maximumTime(time));
    }
    public String maximumTime(String time) {
        String h1 = String.valueOf(time.charAt(0));
        String h2 = String.valueOf(time.charAt(1));
        String m1 = String.valueOf(time.charAt(3));;
        String m2 = String.valueOf(time.charAt(4));;

        if (h1.equals("?")) {
            if (h2.equals("?")) {
                h1 = "2";
                h2 = "3";
            } else {
                if (Integer.valueOf(h2) <= 3) {
                    h1 = "2";
                } else {
                    h1 = "1";
                }
            }
        } else {
            if (h2.equals("?")) {
                if (Integer.valueOf(h1) <= 1) {
                    h2 = "9";
                } else {
                    h2 = "3";
                }
            }
        }
        if (m1.equals("?")) {
            m1 = "5";
        }
        if (m2.equals("?")) {
            m2 = "9";
        }
        return h1 +h2 + ":" + m1 + m2;
    }
}

