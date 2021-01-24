package cn.hd.leetcode.easy._2101;

public class _674 {
    public static void main(String[] args) {
        _674 _ = new _674();
        int [] nums = {1,3,5,7};
        System.out.println(_.findLengthOfLCIS(nums));
    }
    public int findLengthOfLCIS(int[] nums) {
        if (nums.length ==0) {
            return 0;
        }
         if (nums.length==1) {
             return 1;
         }
        int r = 1;
        int i = 0;
        int tmp;
        while (i + 1 < nums.length) {
            if (nums[i+1] > nums[i]) {
                tmp = nums[i+1] - nums[i];
                if ( tmp > r) {
                    r = tmp;
                }
            } else {
                r = 1;
            }
            i++;
        }
        return r;
    }
}
