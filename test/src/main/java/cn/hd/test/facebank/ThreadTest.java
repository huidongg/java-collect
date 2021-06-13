package cn.hd.test.facebank;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ThreadTest {
    public static void main(String[] args) {

    }

    public void lockSupportTest() {
        Thread t2 = new Thread(()->{
            System.out.println("start");
            LockSupport.park();
            System.out.println("continue");
        });
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LockSupport.unpark(t2);
        });
        t2.start();
        t1.start();
    }
}
