package cn.hd.leetcode.exmple;

import java.util.Arrays;
import java.util.LinkedList;

public class _5621 {

    public static void main(String[] args) {
        int [] stu = {1,1,0,0};
        int [] sand = {0,1,0,1};
        _5621 _ = new _5621();
        int r = _.countStudents(stu, sand);
        System.out.println(r);
    }

    public int countStudents(int[] students, int[] sandwiches) {
        LinkedList<Integer> l1 =new LinkedList<Integer>();
        for (int i = 0; i < students.length; i++) {
            l1.add(students[i]);
        }

        int c = 0;
        for (int i = 0; i < sandwiches.length;) {
            int tmp = l1.pop();
            if (tmp == sandwiches[i]) {
                i++;
            } else {
                l1.addLast(tmp);
            }
            c++;
            if (c > (sandwiches.length *  students.length)) {
                break;
            }
        }

        return l1.size();
    }
}
