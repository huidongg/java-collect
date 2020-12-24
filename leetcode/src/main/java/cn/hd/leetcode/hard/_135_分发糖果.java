package cn.hd.leetcode.hard;

import java.util.*;

public class _135_分发糖果 {
    
    public static void main(String[] args) {
        int [] ar = {1,2,2};
        _135_分发糖果 w = new _135_分发糖果();
        System.out.println(w.candy(ar));
    }

    // 头
    private static Nod head;
    // 尾
    private static Nod last;
    
    private static class Nod {
        public int value;
        public int pg;
        public Nod prev;
        public Nod next;
        public Nod (int value, int pg) {
            this.value = value;
            this.pg = pg;
        }

    }

    public static void add(int value, int pg){
        // 创建新节点
        Nod nod = new Nod(value, pg);
        // 为新节点添加数据
        nod.value = value;
        // 如果表头为空直接将新节点作为头
        if (head==null){
            head = last = nod;
        }else {
            nod.prev = last;
            last.next = nod;
            last = nod;
        }
    }

    private int candy(int[] ratings) {
        int len = ratings.length;
        if (len == 1) {
            return 1;
        }
        for (int i = 0; i < len; i++) {
            add(ratings[i], 1);
        }

        往后(head);
        Nod cur = head;
        int i = 0;
        int c = 0;
        while (true) {
            i++;
            System.out.println("第" + i + "个小朋友的积分:" + cur.value + "的苹果:" + cur.pg);
            c+=cur.pg;
            cur = cur.next;
            if (cur == null) {
                break;
            }
        }
        return c;
    }

//    private static void 往前(Nod nod) {
//        while (true) {
//            Nod prevNod = nod.prev;
//            if (prevNod == null) {
//                break;
//            }
//            if (nod.value < prevNod.value && nod.pg >= prevNod.pg) {
//                prevNod.pg++;
//            }
//            nod = prevNod;
//        }
//    }

    private static void 往后(Nod nod) {
        while (true) {
            Nod nextNod = nod.next;
            if (nextNod == null) {
                break;
            }
            if (nod.value > nextNod.value ) {
                if (nod.pg <= nextNod.pg) {
                    nod.pg++;
                }
            } else if (nod.value < nextNod.value) {
                if (nod.pg >= nextNod.pg) {
                    nextNod.pg++;
                }
            }
            nod = nextNod;
        }
    }
}
