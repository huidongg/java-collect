package cn.hd.leetcode.exmple;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class _5622 {
    public static void main(String[] args) {
        int customersP[][] = {{2,3},{6,3},{7,5},{11,3},{15,2},{18,1}};
        _5622 _ = new _5622();
        double r = _.averageWaitingTime(customersP);
        System.out.println(r);
    }
    public double averageWaitingTime(int[][] customers) {
        int dg = customers[0][0] + customers[0][1]; // 递归到时间
        long sum = customers[0][1]; // 总时间
        for (int i = 1; i < customers.length; i++) {
            System.out.println(dg);
            if (dg > customers[i][0]) {
                dg = dg + customers[i][1];
                sum += dg -customers[i][0];
            } else {
                dg = customers[i][0] + customers[i][1];
                sum += customers[i][1];
            }

        }
        BigDecimal s = new BigDecimal(sum);
        BigDecimal g = new BigDecimal(customers.length);
        return s.divide(g, 5, RoundingMode.HALF_UP).doubleValue();
    }
}
