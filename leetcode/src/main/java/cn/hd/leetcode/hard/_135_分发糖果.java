package cn.hd.leetcode.hard;

import java.util.*;

/**
 * 场景有
 * ----
 * 1  1
 * 2  2
 * 3  3
 * ----
 * 3  3
 * 2  2
 * 1  1
 * ----
 * 1  1
 * 2  2
 * 1  1
 * ----
 * 2  2
 * 1  1
 * 2  2
 *
 *
 *
 */
public class _135_分发糖果 {
    
    public static void main(String[] args) {
        int [] ar = {1,2,3,3,3,2,1};
        _135_分发糖果 w = new _135_分发糖果();
        System.out.println(w.candy(ar));
    }

    public int candy(int[] ratings) {
        int len = ratings.length;
        int [] tgs = new int[len];
        for (int i = 0; i < len; i++) {
            tgs[i] = 1;
        }
        for (int i = 0; i < len - 1; i++) {
            int n = i + 1;
            if (ratings[i] > ratings[n]) {
                if (tgs[i] <= tgs[n]) {
                    tgs[i] = tgs[n] + 1;
                    // 当前果子+1， 需要往前计算
                    int c = i;
                    while (true) {
                        int p = c - 1;
                        if (p >= 0) {
                            // 不能等
                            if (ratings[c] < ratings[p] ) {
                                if (tgs[c] >= tgs[p]) {
                                    tgs[p] = tgs[c] + 1;
                                }
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                        c--;
                    }
                }
            } else if (ratings[i] < ratings[n]) {
                if (tgs[i] >= tgs[n]) {
                    tgs[n] = tgs[i] + 1;
                }
            }
        }
        int c = 0;
        for (int i = 0; i < len; i++) {
            System.out.println(tgs[i]);
            c += tgs[i];
        }
        return c;
    }
}
