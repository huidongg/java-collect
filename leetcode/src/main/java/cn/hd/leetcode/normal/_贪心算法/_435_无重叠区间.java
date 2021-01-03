package cn.hd.leetcode.normal._贪心算法;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。
 *
 * 注意:
 *
 * 可以认为区间的终点总是大于它的起点。
 * 区间 [1,2] 和 [2,3] 的边界相互“接触”，但没有相互重叠。
 */
public class _435_无重叠区间 {
    public static void main(String[] args) {
        _435_无重叠区间 _ = new _435_无重叠区间();
        int[][] intervals = {{1,2}, {2,3}};
        int[][] arrrr = {{1,2}, {1,2}, {1,2}};
        int[][] arrr = {{1,2}, {2, 3}, {3,4}, {1, 3}};      //1
        int [][] arr = {{1,100},{11,22},{1,11},{2,12}};     //2
        // int [][] ar = {{0,2},{1,3},{2,4},{3,5},{4,6}};   2
        int [][] ar = {{0,2},{1,3},{1,3},{2,4},{3,5},{3,5},{4,6}};
        System.out.println(_.eraseOverlapIntervals(ar));
    }

    /**
     * 1, 找出关联数据, 构造Map<i, List<关联下标>> map根据value长度排序, 最大在前,value的长度必须大于0
     * 2, 一次删除map从大到小, 知道map为0, 删除次数就是返回值
     *
     *  @param intervals
     * @return
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        List<Sec> secList = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            Sec sec = new Sec();
            for (int j = 0; j < intervals.length; j++) {
                if (i == j) {
                    continue;
                }
                boolean r = mix(intervals[i], intervals[j]);
                if (r) {
                    System.out.println(i + " **** " + j);
                    if (sec.index == null) {
                        sec.index = i;
                    }
                    sec.list.add(j);
                }
            }
            if (sec.index != null) {
                secList.add(sec);
            }
        }
        int c = 0;
        while (secList.size() > 0) {

            secList.sort((x, y)->y.list.size() - x.list.size());
            Sec sec = secList.get(0);
            System.out.println("sec.index=" + sec.index + "--sec.list=" + sec.list + "--secList.size=" + secList.size());

            if (sec.list.size() == 1) {
                System.out.println(secList.size());
                c += secList.size() / 2;
                break;
            }
            secList.remove(0);
            c++;
            for (int i = 0; i < secList.size();i++) {
                if (sec.list.contains(secList.get(i).index)) {
                    boolean b = secList.get(i).list.remove(sec.index);
                    if (secList.get(i).list.size() == 0) {
                        secList.remove(i);
                        i--;
                    }
                }
            }
        }
        return c;
    }

    class Sec {
        Integer index;
        List<Integer> list = new ArrayList<>();
    }

    private boolean mix(int [] x, int [] y) {
        if (x[0] >= y[0] && x[1] <= y[1]) {
            return true;
        }
        if (x[0] <= y[0] && x[1] >= y[1]) {
            return true;
        }
        if (x[0] <= y[0] && x[1] > y[0]) {
            return true;
        }
        if (x[0] >= y[0] && x[1] < y[0]) {
            return true;
        }
        return false;
    }
}
