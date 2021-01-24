package cn.hd.leetcode.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个字符串 s 和 t，判断它们是否是同构的。
 *
 * 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
 *
 * 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/isomorphic-strings
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _205同构字符串 {

    public static void main(String[] args) {
        _205同构字符串 _205 = new _205同构字符串();
        String s = "ab";
        String t = "aa";
        System.out.println(_205.isIsomorphic(s, t));
    }

    public boolean isIsomorphic(String s, String t) {
        HashMap<Character, Character> map = new HashMap<Character, Character>();
        char [] chars = s.toCharArray();
        char [] chart = t.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character tmp = map.get(chars[i]);
            if (tmp == null) {
                if (map.values().contains(chart[i])) {
                    return false;
                }
                map.put(chars[i], chart[i]);
            } else {
                if (tmp.charValue() != chart[i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
