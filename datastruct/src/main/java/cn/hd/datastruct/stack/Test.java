package cn.hd.datastruct.stack;

import java.util.Random;

public class Test {
    public static void main(String[] args)  {
        MyStack myStack = new MyStack(3);

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                Random rd = new Random();
                int r = 1 + rd.nextInt(5);
                for (int j = 0; j < r; j++) {
                    myStack.push(i + "|" + j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                Random rd = new Random();
                int r = 1 + rd.nextInt(5);
                for (int j = 0; j < r;) {
                    Object o = myStack.pop();
                    try {
                        if (o == null) {
                            Thread.sleep(100);
                        } else {
                            Thread.sleep(1000);
                            j++;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
