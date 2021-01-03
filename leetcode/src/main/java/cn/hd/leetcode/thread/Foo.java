package cn.hd.leetcode.thread;

public class Foo {
    public Foo() {

    }

    public static void main(String[] args) throws Exception {
        Runnable first = () ->{
            System.out.print("first");
        };
        Runnable second = () ->{
            System.out.print("second");
        };
        Runnable third = () ->{
            System.out.print("third");
        };
        int [] x = {3, 1, 2};
        Foo foo = new Foo();
        
    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
