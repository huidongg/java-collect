package cn.hd.leetcode.easy;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组, nums和一个目标值target,请你在该数组中找出和未目标值的那两个整数,并返回他们的数组下标
 * c语言才是性能之王
 */
public class _1_俩数之和 {
    public static void main(String[] args) {
        int [] nums = {2, 5, 5, 11};
        int target = 10;
        int [] res = exec3(nums, target);
        System.out.println(JSON.toJSONString(res));
    }

    /**
     * 
     * @param nums
     * @param target
     * @return
     */
    private static int [] exec1(int [] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    /**
     * 差值比较
     * @param nums
     * @param target
     * @return
     */
    private static int [] exec2(int [] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            int z = target - nums[i];
            for (int j = i+1; j < nums.length; j++) {
                if (z == nums[j]) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    /**
     * 差值hash比较
     * @param nums
     * @param target
     * @return
     */
    private static int [] exec3(int [] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length - 1; i++) {
            if (map.containsKey(nums[i])) {
                return new int[] {map.get(nums[i]), i};
            } else {
                map.put(target - nums[i], i);
            }
        }
        return new int[0];
    }
}
