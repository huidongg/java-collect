package cn.hd.leetcode.easy._20201229;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Collections;

/**
 * 有一堆石头，每块石头的重量都是正整数。
 *
 * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
 *
 * 如果 x == y，那么两块石头都会被完全粉碎；
 * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
 * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
 *
 *  
 *
 * 示例：
 *
 * 输入：[2,7,4,1,8,1]
 * 输出：1
 * 解释：
 * 先选出 7 和 8，得到 1，所以数组转换为 [2,4,1,1,1]，
 * 再选出 2 和 4，得到 2，所以数组转换为 [2,1,1,1]，
 * 接着是 2 和 1，得到 1，所以数组转换为 [1,1,1]，
 * 最后选出 1 和 1，得到 0，最终数组转换为 [1]，这就是最后剩下那块石头的重量。
 *  
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/last-stone-weight
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 我的想法, 一个方法, 返回 x次大,y最大, 然后将差值(如果大于0)插入, 直到只能返回一个, 返回这个值, 如果没有返回0
 */
public class _1046_后一块石头的重量 {
    public static void main(String[] args) {
        //int [] stones = {2,7,4,1,8,1};
        int [] stones = {1, 4, 1};
        _1046_后一块石头的重量 _ = new _1046_后一块石头的重量();
        System.out.println(_.lastStoneWeight(stones));
    }

    public int lastStoneWeight(int [] stones) {
        int [] tmp = stones;
        while (tmp.length >= 2) {
            reverseOrder(tmp);
            // 获取最大
            int y = tmp[0];
            int x = tmp[1];
            int z = y - x;
            tmp[1] = z;
            int p = z > 0 ? 1 : 2;
            int l = tmp.length - p;
            int [] tmps = new int[l];
            for (int i = 0; i < l; i++) {
                tmps[i] = tmp[i+p];
            }
            tmp = tmps;
        }
        if (tmp.length == 0) {
            return 0;
        } else {
            return tmp[0];
        }
    }


    private void reverseOrder(int [] stones) {
        // 排序
        int tmp;
        for (int i = 0; i < stones.length; i++) {
            for (int j = i+1; j < stones.length; j++) {
                if (stones[i] < stones[j]) {
                    tmp = stones[i];
                    stones[i] = stones[j];
                    stones[j] = tmp;
                }
            }
        }
         // Arrays.sort(arr, Collections.reverseOrder());
    }
}
