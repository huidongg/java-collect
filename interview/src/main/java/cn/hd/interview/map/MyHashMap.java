package cn.hd.interview.map;

import java.util.HashMap;

/**
 * b站学习
 * https://www.bilibili.com/video/BV1Pp411d7kB?from=search&seid=11409296111176896627
 */
public class MyHashMap {
    public static void main(String[] args) {
//        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
//        hashMap.put(1, "zs");
//        hashMap.put(2, "ls");
//        hashMap.put(3, "ww");
        hd1("12");

    }

    /**
     * Object的hashcode是ascii值
     * @param o
     */
    private static void hd (Object o) {
        System.out.println(o.hashCode());
    }
    /**
     * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     *  1 * 31^1 + 2 * 1
     */
    private static void hd1 (String o) {
        System.out.println(o.hashCode());
    }
}
