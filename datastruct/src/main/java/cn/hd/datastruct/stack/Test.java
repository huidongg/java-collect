package cn.hd.datastruct.stack;

import java.util.Random;

public class Test {
    public static void main(String[] args) throws Exception {
        MyStack myStack = new MyStack(3);

        new Thread(()->{

        }).start();
        for (int i = 0; i < 100; i++) {
            Random rd = new Random();
            int r = 1 + rd.nextInt(5);
            for (int j = 0; j < r; j++) {
                myStack.push(i + "|" + j);
                Thread.sleep(1000);
            }
        }
    }
}
