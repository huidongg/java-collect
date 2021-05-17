package cn.hd.test.list;

import java.util.ArrayList;
import java.util.List;

/**
 * 是否包含判断
 */
public class ListContain {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("2");
        list1.add("3");
        list1.add("1");
        list1.add("4");
        List<String> list2 = new ArrayList<>();
        list2.add("4");
        list2.add("1");
        list2.add("5");

        System.out.println(list1.containsAll(list2));
    }
}
