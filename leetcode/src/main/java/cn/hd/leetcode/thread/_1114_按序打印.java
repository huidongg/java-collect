package cn.hd.leetcode.thread;

public class _1114_按序打印 {
    public static void main(String[] args) {
        Thread first = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("first:" + i);
            } }, "first");
        Thread second = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("second:" + i);
            } }, "second");

        first.start();
        try {
            first.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        second.start();
    }


}
