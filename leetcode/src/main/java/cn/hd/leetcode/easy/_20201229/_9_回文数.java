package cn.hd.leetcode.easy._20201229;

public class _9_回文数 {
    public static void main(String[] args) {
        _9_回文数 _9 = new _9_回文数();
        int x = 10601;
        System.out.println(_9.isPalindrome(x));
    }
    public boolean isPalindrome(int x) {
        //边界判断
        if (x < 0) return false;
        int div = 1;
        //
        while (x / div >= 10) div *= 10;
        while (x > 0) {
            int left = x / div;
            int right = x % 10;
            if (left != right) return false;
            x = (x % div) / 10;
            div /= 100;
        }
        return true;
    }


//
//    public boolean isPalindrome(int x) {
//        if (x < 0) {
//            return false;
//        }
//        int len = getLen(x);
//        int b = len / 2;
//        int tmp = x;
//        for (int i = 0; i < b; i++) {
//            int s = tmp / (int) Math.pow(10, len - 1 - i);
//            tmp = x % (int) Math.pow(10, len - 1 - i);
//            int e = (x / (int) Math.pow(10, i)) % 10;
//            System.out.println(s+" "+e);
//            if (s != e) {
//                return false;
//            }
//        }
//        return true;
//    }

    private int getLen(int x) {
        int len = 0;
        while (x > 0) {
            len++;
            x = x / 10;
        }
        return len;
    }
}
