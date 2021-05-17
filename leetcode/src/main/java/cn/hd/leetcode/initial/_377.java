package cn.hd.leetcode.initial;

import java.util.HashMap;
import java.util.Map;

public class _377 {

    public static void main(String[] args) {
        _377 _ = new _377();
        int [] nums = {2};
        int target = 3;
        System.out.println(_.combinationSum4(nums, target));
    }

    Map<Integer, Integer> map = new HashMap<>();
    public int combinationSum4(int[] nums, int target) {
        return backtrack(nums, target);
    }

    private int backtrack(int [] nums, int remains) {
        if (remains == 0) {
            return 1;
        }
        if (map.containsKey(remains)) {
            return map.get(remains);
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (remains >= nums[i]) {
                res += backtrack(nums, remains - nums[i]);
            }
        }
        map.put(remains, res);
        return res;
    }

}
