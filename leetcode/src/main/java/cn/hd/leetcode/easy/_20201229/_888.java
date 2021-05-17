package cn.hd.leetcode.easy._20201229;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class _888 {
    public static void main(String[] args) {
        _888 _ = new _888();
        int [] A = {1, 5, 2};
        int [] B = {2, 4};
        int [] r = _.fairCandySwap(A, B);
        System.out.println(JSON.toJSONString(r));
    }
    public int[] fairCandySwap(int[] A, int[] B) {
        int [] r = new int[2];
        int a = Arrays.stream(A).sum();
        int b = Arrays.stream(B).sum();
        Set<Integer> bb = new HashSet<>();
        for (int i = 0; i < B.length; i++) {
            bb.add(B[i]);
        }
        int p = (a+b)/2;
        for (int i = 0; i < A.length; i++) {
            int c = p - (a - A[i]);
            if (bb.contains(c)) {
                r[0] = A[i];
                r[1] = c;
                break;
            }

        }
        return r;
    }
}
