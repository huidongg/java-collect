package cn.hd.leetcode.normal._贪心算法;

/**
 * 动态规划
 */
public class _122买卖股票的最佳时机II {
    public static void main(String[] args) {
        int [] prices = {1, 2, 7, 3, 5};
        System.out.println(maxProfit(prices));
    }

    public static int maxProfit(int[] prices) {
        int ans = 0;
        int n = prices.length;
        for (int i = 1; i < n; ++i) {
            ans += Math.max(0, prices[i] - prices[i-1]);
        }
        return ans;
    }
}
