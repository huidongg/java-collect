package cn.hd.leetcode.hard;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class _480 {

    public static void main(String[] args) {
//        _480 _ = new _480();
//        int[] nums = {1,3,-1,-3,5,3,6,7};
//        int k = 3;
//        double[] d = _.medianSlidingWindow(nums, k);
//        System.out.println(JSON.toJSONString(d));

        List<Integer> list = new ArrayList<>();
        list.add(-2);
        list.add(0);
        list.add(3);
        list.add(5);

        int x = 2;
        list.add(x);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) >= x) {
                int  q = list.get(i);
                for (int j = list.size() - 1; j < list.size(); j++) {
                    list.set(j, list.get(j-1));

                }
            }
        }
        System.out.println(list);
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        List<Double> r = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length - k + 1; i++) {
            if (list.size() == 0) {
                // 获得窗口数据
                for (int j = i; j < i+k; j++) {
                    list.add(nums[j]);
                }
                list.sort((x, y)->x.compareTo(y));
            } else {
                for (int m = 0; m < k; m++) {
                    if (list.get(m).equals(nums[i-1])) {
                        list.remove(m);
                        break;
                    }
                }
                int z = nums[i+k-1];
                list.add(z);
                // 排序
                for (int n = 0; n < k; n++) {
                    if (list.get(n)>= z) {
                        for (int g = n+1; g < k; g++) {
                            list.set(g, list.get(g-1));
                        }
                        list.set(n, z);
                    }
                }
            }
            System.out.println(list);
            // 窗口数据排序
            // 奇数
            if (k % 2 == 1) {
                r.add(Double.valueOf(list.get((k/2))));
            }
            // 偶数取中间两位,相加除
            else {
                Integer q = list.get((k/2) - 1);
                Integer h = list.get((k/2));
                BigDecimal qh = new BigDecimal(q).add(new BigDecimal(h));
                BigDecimal b = qh.divide(new BigDecimal("2"), 5, RoundingMode.HALF_UP);
                r.add(b.doubleValue());
            }
        }
        double[] rd= new double[r.size()];
        for (int i= 0; i < r.size(); i++) {
            rd[i] = r.get(i);
        }
        return rd;
    }
}
