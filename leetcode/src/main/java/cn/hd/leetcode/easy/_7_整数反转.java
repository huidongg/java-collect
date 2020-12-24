package cn.hd.leetcode.easy;

public class _7_整数反转 {
    public static void main(String[] args) {
//        System.out.println(niuReverse(1234567892));
//
//        reverse(1234567892);
        // 48 = 58

       System.out.println(benReverse(-1234));
    }


    private static int niuReverse(int x) {
        long n = 0;
        while (x != 0) {
            n = n * 10 + x % 10;
            x = x / 10;
        }
        return (int) n == n ? (int) n : 0;
    }

    /**
     * 我的思路,  每个整数的组成: 1 * 10^3 + 2 * 10 ^ 2 + 3 * 10 ^ 1 + 4 * 10 ^ 0
     * 1, 获得数字长度
     * 2, 循环数字长度, 依次获得数字位数的数字,
     * 3, 对应位数的10的幂,递增就是反转数
     * @param x
     * @return
     */
    private static int reverse(int x) {
        int y = 0;
        while (x != 0) {
            //System.out.println(x % 10);
            y = y * 10 + x % 10;
            System.out.println(y);
            x = x / 10;
        }
        return y;
    }

    private static int benReverse(int x) {
        int fh;
        String sbr;
        if (x >= 0) {
            sbr = String.valueOf(x);
            fh = 1;
        } else {
            sbr = String.valueOf(x).substring(1);
            fh = -1;
        }
        char [] y = sbr.toCharArray();
        char tmp;
        for (int i = 0;i < y.length / 2; i++) {
            int j = y.length - 1 - i;
            tmp = y[i];
            y[i] = y[j];
            y[j] = tmp;
        }
        long r = Long.valueOf(String.valueOf(y));
        return (int) r == r ? (int) r * fh : 0;

    }

}
