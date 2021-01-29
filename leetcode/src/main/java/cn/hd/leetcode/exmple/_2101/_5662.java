package cn.hd.leetcode.exmple._2101;

import java.util.Arrays;
import java.util.Collections;

public class _5662 {
    public static void main(String[] args) {
        _5662 _ = new _5662();
        String a = "dcba";
        String b = "eca";
        System.out.println(_.minCharacters(a, b));
        // a 97  z 122
        System.out.println((int)'z');

    }

    public int minCharacters(String a, String b) {
        
        char [] ar = a.toCharArray();
        Character[] arr = new Character[ar.length];
        for (int i = 0; i < ar.length; i++) {
            arr[i] = new Character(ar[i]);
        }
        char [] br = b.toCharArray();
        Character[] brr = new Character[br.length];
        for (int i = 0; i < br.length; i++) {
            brr[i] = new Character(br[i]);
        }
        // a < b
        int c1 = 0;
        // 从小到大
        // , Collections.reverseOrder()
        Arrays.sort(arr);
        Arrays.sort(brr);

        if (brr[0] == 'a') {
            c1 = 0;
        } else {
            
        }
     

        // a = b
        return c1;
    }
}
