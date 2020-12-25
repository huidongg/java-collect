package cn.hd.leetcode.easy;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
 *
 * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/assign-cookies
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _455_分发饼干 {
    public static void main(String[] args) {
        int [] g = {1,2,3, 4}; int [] s = {1, 5,3};
        _455_分发饼干 _455 = new _455_分发饼干();
        System.out.println(_455.findContentChildren(g, s));
    }

    /**
     * 统计
     * @param g 胃口
     * @param s 饼干
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int t = 0;
        int n = 0;
        for (int i = 0; i < g.length; i++) {
            int j = n;
            for (; j < s.length; j++) {
                if (g[i] <= s[j]) {
                    t++;
                    n = j + 1;
                    break;
                }
            }
        }
        return t;
    }
}
