package cn.hd.leetcode.initial;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class 台阶 {
    private static List<List<Integer>> rgList = new ArrayList<>();
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        dg(4, list);

        System.out.println(JSON.toJSONString(rgList));
    }

    private static void dg(int z, List<Integer> list) {
        int x = z - 1;
        int y = z - 2;
        if (x >= 0) {
            List<Integer> tlist = new ArrayList<>();
            tlist.addAll(list);
            rgList.add(tlist);
            tlist.add(1);
            System.out.println(1 + "  " + x);
            dg(x, tlist);
        }
        if (y >= 0) {
            List<Integer> t2list = new ArrayList<>();
            t2list.addAll(list);
            rgList.add(t2list);
            t2list.add(2);
            System.out.println(2 + "  " + y);
            dg(y, t2list);
        }
        return;
    }
}
