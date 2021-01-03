package cn.hd.leetcode.easy;

public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int s = toInt(l1) + toInt(l2);
        return toList(s);
    }

    private void intor(ListNode l1 ) {
        while (true) {
            System.out.println(l1.val);
            l1 = l1.next;
            if (l1 == null) {
                break;
            }
        }
    }

    private int toInt(ListNode node) {
        int i = 1;
        int s = 0;
        while (true) {
            s = s + node.val * i;
            node = node.next;
            i = i * 10;
            if (node == null) {
                break;
            }
        }
        return s;
    }

    private ListNode toList(int x) {
        ListNode node1 = new ListNode(x % 10);
        toListD(x, node1);
        return node1;
    }

    /**
     * 递归构造
     * @param x
     * @param node
     */
    private void toListD(int x, ListNode node) {
        x = x / 10;
        if (x > 0) {
            ListNode node1 = new ListNode(x % 10);
            node.next = node1;
            toListD(x, node1);
        }
    }

    public static class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }
}
