package cn.hd.test;

public class Test2 {
    public static void main(String[] args) {
        String user = "1,333,4444";
        int r = user.indexOf(",");
        System.out.println(new StringBuffer(user).insert(r + 1, "22,"));


        int [] ar1 = {1,3,5,0, 0, 0};
        int m = 3;
        int [] ar2 = {2, 4, 6};
        int n = 3;

        int p1 = 0;
        int p2 = 0;
        int cur = 0;
        int [] curArr = new int[m+n];
        while (p1 < m || p2 < n) {
            if (p1 == m) {
                cur = ar1[p2];
            }
        }
    }
}
