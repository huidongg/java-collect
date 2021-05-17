package cn.hd.test;

import java.time.LocalTime;

public class RandomTest {
    public static void main(String[] args) {
        System.out.println(LocalTime.parse("00:00:10.68").toSecondOfDay());
    }
}
