package cn.hd.leetcode.normal._动态规划;

/**
 * 我就是用最愚蠢的办法来解题
 */
public class _迈步子上楼梯 {
    int [] x;
    int rs = 3;

    public static void main(String[] args) {
        // 111, 12, 21
        TreeNode root = new TreeNode(0);

    }

     void dg(TreeNode node) {
        if (x[0] + 1 <= rs) {
            node.left = new TreeNode(1);
            dg(node.left);
        }
        if (x[0] + 2 <= rs) {
             node.right = new TreeNode(2);
             dg(node.right);
        }

    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
