package cn.hd.leetcode.exmple._226;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _1743 {
    public static void main(String[] args) {
        _1743 _ = new _1743();
        int[][] adjacentPairs = {{3, 2}, {4, 5}, {2, 1}, {4, 3}};
        int [] rs = _.restoreArray(adjacentPairs);
        System.out.println(JSON.toJSONString(rs));
    }

    public int[] restoreArray(int[][] adjacentPairs) {
        Map<Integer, Dq> map = new HashMap<>();
        for (int [] ad : adjacentPairs) {
            Dq dq = map.get(ad[0]);
            if (dq == null) {
                dq = new Dq();
                dq.setHou(ad[1]);
                map.put(ad[0], dq);
            } else {
                if (dq.getQian() == null) {
                    dq.setHou(ad[1]);
                } else {
                    dq.setQian(ad[1]);
                }
            }
            dq = map.get(ad[1]);
            if (dq == null) {
                dq = new Dq();
                dq.setHou(ad[0]);
                map.put(ad[1], dq);
            } else {
                if (dq.getQian() == null) {
                    dq.setHou(ad[0]);
                } else {
                    dq.setQian(ad[0]);
                }
            }
        }

        System.out.println(JSON.toJSONString(map));
        return null;
    }

    @Data
    static class Dq {
         private Integer qian;
         private Integer hou;
    }


    
}
