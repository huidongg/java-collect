package cn.hd.datastruct.stack;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * 底层使用数组存储数据
 */
public class MyStack<T> {
    private static Logger log = Logger.getLogger(MyStack.class.toString());
    private Object [] elements;
    private int mark = 0;
    private int spare;
    private int step = 10;

    private Lock lock = new ReentrantLock();
    public MyStack() {
        elements = new Object[step];
        spare = 10;
    }

    public MyStack(int paramStep) {
        step = paramStep;
        elements = new Object[step];
        spare = step;
    }

    /**
     * 压入元素
     */
    public void push(T element) {
        lock.lock();
        log.info("压入元素:" + element);
        elements[mark] = element;
        mark++;
        spare--;
        if (elements.length == mark) {
            elements = Arrays.copyOf(elements, elements.length + step);
            spare+=step;
            log.info("扩容元素数组, 空余:" + spare + ",标记位:" + mark);
        }
        lock.unlock();
    }
    /**
     * 弹出元素
     */
    public T pop() {
        lock.lock();
        log.info("弹出元素");
        if (mark - 1 < 0) {
            throw new RuntimeException("stack is null");
        }
        Object element = elements[mark - 1];
        // 清空元素
        mark--;
        spare++;
        // 数组需要压缩一个step
        if (spare == step) {
            elements = Arrays.copyOf(elements, elements.length - step);
            spare-=10;
            log.info("压缩元素数组, 空余:" + spare + ",标记位:" + mark);
        }
        lock.unlock();
        return (T)element;
    }
}
