package cn.hd.leetcode.easy._20201229;

public class _14_最长公共前缀 {
    public static void main(String[] args) {
        _14_最长公共前缀 _ = new _14_最长公共前缀();
        //String [] strs = {"flower","flow","flight", "fuck"};
        String [] strs = {"a", "ab"};
        System.out.println(_.longestCommonPrefix(strs));
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        // 公共前缀
        StringBuilder sb = new StringBuilder();
        sb.append(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            char [] tmp = strs[i].toCharArray();
            if (tmp.length < sb.length()) {
                sb.delete(tmp.length, sb.length());
            }
            for (int j = 0; j < sb.length(); j++) {
                if (tmp[j] != sb.charAt(j)) {
                    sb.delete(j, sb.length());
                    break;
                }
            }
        }
        return sb.toString();
    }
}
