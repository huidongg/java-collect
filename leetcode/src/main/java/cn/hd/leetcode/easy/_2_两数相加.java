package cn.hd.leetcode.easy;

import com.alibaba.fastjson.JSON;

import java.math.BigInteger;

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

    /**
     * TODO BigInteger 最愚蠢的写法
     * @param args
     */
    public static void main(String[] args) {
        BigInteger x = BigInteger.valueOf(9);
        BigInteger y = BigInteger.valueOf(9999999991l);

        _2_两数相加 _2_ = new _2_两数相加();
        ListNode l1 = _2_.toList(x);
        ListNode l2 = _2_.toList(y);
        _2_.intor(l1);
        _2_.intor(l2);
        ListNode l3 = _2_.addTwoNumbers(l1, l2);
        _2_.intor(l3);


    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        BigInteger s = toBigInteger(l1).add(toBigInteger(l2));
        return toList(s);
    }

    private void intor(ListNode l1 ) {
        while (true) {
            System.out.print(l1.val + " ");
            l1 = l1.next;
            if (l1 == null) {
                break;
            }
        }
        System.out.println();
    }

    private BigInteger toBigInteger(ListNode node) {
        BigInteger i = BigInteger.valueOf(1);
        BigInteger s = BigInteger.ZERO;
        while (true) {
            s = (s.add(BigInteger.valueOf(node.val).multiply(i)));
            node = node.next;
            i = i.multiply(BigInteger.valueOf(10));
            if (node == null) {
                break;
            }
        }
        System.out.println(s);
        return s;
    }

    private ListNode toList(BigInteger x) {
        ListNode node1 = new ListNode(x.remainder(BigInteger.valueOf(10)).intValue());
        toListD(x, node1);
        return node1;
    }

    /**
     * 递归构造
     * @param x
     * @param node
     */
    private void toListD(BigInteger x, ListNode node) {
        x = x.divide(BigInteger.valueOf(10));
        if (x.compareTo(BigInteger.ZERO) > 0) {
            ListNode node1 = new ListNode(x.remainder(BigInteger.valueOf(10)).intValue());
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
