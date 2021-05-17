package cn.hd.leetcode.initial;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * 核心思想, 将重复的数移到最后
 */
public class 删除排序数组中的重复项 {
    public static void main(String[] args) {
        删除排序数组中的重复项 sc = new 删除排序数组中的重复项();
        int [] nums = {1, 3, 3, 4, 4, 4, 5};
        int len = sc.removeDuplicates(nums);
        System.out.println(len);
        System.out.println(JSON.toJSONString(nums));

        int[] numsnew = Arrays.copyOf(nums, len);
        System.out.println(JSON.toJSONString(numsnew));
    }

    /**
     * 双指针解决问题
     * @param nums
     * @return
     */
    public int removeDuplicates(int [] nums) {

        //边界条件判断
        if (nums == null || nums.length == 0)
            return 0;
        int left = 0;
        //如果左指针和右指针指向的值一样，说明有重复的，
        //这个时候，左指针不动，右指针继续往右移。如果他俩
        //指向的值不一样就把右指针指向的值往前挪
        for (int right = 1; right < nums.length; right++) {
            if (nums[left] != nums[right])
                nums[++left] = nums[right];
        }
        return ++left;
    }
}
