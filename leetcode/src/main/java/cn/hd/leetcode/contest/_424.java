package cn.hd.leetcode.contest;

public class _424 {
    public static void main(String[] args) {
        String s = "BCDAAQZA";
        int k = 2;
        _424 _ = new _424();
        System.out.println(_.characterReplacement(s, k));
    }

    public int characterReplacement(String s, int k) {
        // 从开始遍历,找下一个,遇到非下一个, k-1, 保持k>=0,  或者结束, 统计值 , 循环, 那个最大就是那个
        char [] chars = s.toCharArray();
        int max = 0;
        for (int i = 0; i < chars.length; i++) {

            if (i > 0) {
                if (chars[i] == chars[i-1]){
                    continue;
                }
            }
            int t = k;
            int q = 1;
            // 先往前再往后
            for (int m = i-1; m >=0; m--) {
                if (chars[i] != chars[m]) {
                    if (t > 0) {
                        t--;
                    } else {
                        break;
                    }
                }
                q++;
            }
            for (int n = i+1; n < chars.length; n++) {
                if (chars[i] != chars[n]) {
                    if (t > 0) {
                        t--;
                    } else {
                        break;
                    }
                }
                q++;
            }
            if (max < q) {
                max = q;
            }

            t = k;
            q = 1;
            // 先往后再往前
            for (int n = i+1; n < chars.length; n++) {
                if (chars[i] != chars[n]) {
                    if (t > 0) {
                        t--;
                    } else {
                        break;
                    }
                }
                q++;
            }
            for (int m = i-1; m >=0; m--) {
                if (chars[i] != chars[m]) {
                    if (t > 0) {
                        t--;
                    } else {
                        break;
                    }
                }
                q++;
            }
            if (max < q) {
                max = q;
            }
        }
        return max;
    }
}
