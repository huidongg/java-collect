package cn.hd.leetcode.easy;

import java.util.LinkedList;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _2_两数相加 {

    private ListNode node1 = null;
    private ListNode node2 = null;
    
    public static void main(String[] args) {
        int x = 1234;
        int y = 566;
        _2_两数相加 _2_ = new _2_两数相加();
        ListNode l1 = _2_.build(x);
        ListNode cur = l1;
        while (true) {
            System.out.println(cur.val);
            cur = cur.next;
            if (cur == null) {
                break;
            }
        }
    }

    private ListNode build(int x) {
        ListNode node1 = new ListNode(x % 10);
        int y = x;
        int y = y / 10;
        ListNode node2 = new ListNode(y % 10);
        node1.next = node2;
        y = y / 10;
        ListNode node3 = new ListNode(y % 10);
        node2.next = node3;
        y = y / 10;
        ListNode node4 = new ListNode(y % 10);
        node3.next = node4;
        return node1;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        return null;
    }

    public static class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }

}
