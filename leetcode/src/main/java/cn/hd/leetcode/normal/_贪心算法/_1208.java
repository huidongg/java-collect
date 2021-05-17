package cn.hd.leetcode.normal._贪心算法;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class _1208 {
    public static void main(String[] args) {
        // 4
//        String s = "pxezla";
//        String t = "loewbi";
//        int maxCost = 25;
        // 3
        String s = "krpgjbjjznpzdfy";
        String t = "nxargkbydxmsgby";
        int maxCost = 14;
      // 1
//        String s = "abcd";
//        String t = "cdef";
//        int maxCost = 3;

//        String s = "abcd";
//        String t = "acde";
//        int maxCost = 0;
        // 2
//        String s = "krrgw";
//        String t = "zjxss";
//        int maxCost = 19;

//        System.out.println((int)s.charAt(0));
//        System.out.println(Math.abs(-11));

        System.out.println(equalSubstring(s, t, maxCost));

    }

    @Data
    static class Chk {
        private String s;
        private int t;
        private int maxCost;
        private int rg;
    }

    public static int equalSubstring(String s, String t, int maxCost) {
        int tmpMaxCost = maxCost;
        int rmax = 0;
        int r = 0;
        int flag = 0;
        for (int i = 0; i < s.length();) {
            int ss = s.charAt(i);
            int tt = t.charAt(i);
            int dj = Math.abs(ss - tt);
            System.out.println(dj + "  " + tmpMaxCost);
            if (dj > tmpMaxCost) {
                // 重复
                if (flag == 1) {
                    i++;
                }
                if (r > rmax) {
                    rmax = r;
                }
                // 从新计算
                tmpMaxCost = maxCost;
                r = 0;
                flag = 1;
            } else  {
                if (dj < tmpMaxCost) {
                    tmpMaxCost -= dj;
                }
                r++;
                i++;
            }
        }
        return rmax;
    }

}
