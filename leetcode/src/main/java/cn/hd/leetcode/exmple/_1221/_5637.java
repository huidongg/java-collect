package cn.hd.leetcode.exmple._1221;

public class _5637 {

    public static void main(String[] args) {
        _5637 _ = new _5637();
        String s = "textbook";
        boolean b = _.halvesAreAlike(s);
        System.out.println(b);
    }

    public boolean halvesAreAlike(String s) {
        char [] yy = {'a','e','i','o','u','A','E','I','O','U'};
        char [] a = s.substring(0, s.length() / 2).toCharArray();
        char [] b = s.substring(s.length() / 2).toCharArray();
        int ay = 0;
        int by = 0;
        int q;
        for (int i = 0; i < a.length; i++) {
            q = 0;
            for (int j = 0; j < yy.length; j++) {
                if (a[i] == yy[j] && (q == 0 || q == 2)) {
                    ay++;
                    q+=1;
                }
                if (b[i] == yy[j] && (q == 0 || q == 1)) {
                    by++;
                    q+=2;
                }
            }
        }
        System.out.println(a);
        System.out.println(b);

        return ay == by;

    }
}
