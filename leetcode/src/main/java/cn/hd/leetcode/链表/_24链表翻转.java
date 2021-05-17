package cn.hd.leetcode.链表;

import lombok.Data;

import java.math.BigDecimal;

public class _24链表翻转 {
    
    private ListNode head;

    public static void main(String[] args) {
        System.out.println(new BigDecimal("2.10").stripTrailingZeros().toString());

        _24链表翻转 _ = new _24链表翻转();
        for (int i = 1; i <= 5; i++) {
            _.addNode(i);
        }
        //_.print(_.head);
        _.head = _.reverseList1(_.head);
        _.print(_.head);

    }
    
    public ListNode reverseList(ListNode head) {
        ListNode node0 = head;
        ListNode node1 = node0.next;
        node0.next = null;
        ListNode node2 = node1.next;
        node1.next = node0;
        ListNode node3 = node2.next;
        node2.next = node1;
        ListNode node4 = node3.next;
        node3.next = node2;
        ListNode node5 = node4.next;
        node4.next = node3;
        return node4;
    }

    public ListNode reverseList1(ListNode head) {
        ListNode nodeCur = head;
        ListNode nodePrev = null;
        while (nodeCur != null) {
            ListNode nodeNext = nodeCur.next;
            nodeCur.next = nodePrev;
            nodePrev = nodeCur;
            nodeCur = nodeNext;
        }
        return nodePrev;
    }


    private void addNode(int x) {
        if (head == null) {
            head = new ListNode(x);
            return;
        }
        ListNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new ListNode(x);
    }

    private void print(ListNode head) {
        ListNode cur = head;
        while (cur!= null) {
            System.out.println(cur.val);
            cur = cur.next;
        }
    }

    @Data
    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
