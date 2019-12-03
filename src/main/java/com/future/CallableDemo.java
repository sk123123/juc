package com.future;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/24 9:11
 */
public class CallableDemo extends AbstractQueuedSynchronizer implements Callable<String> {
    private int  age;
    private ReentrantLock lock = new ReentrantLock();

    public CallableDemo(int  age){
        this.age = age;
    }
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    public String call() throws Exception {
        Thread.sleep(1000);
//        new Thread().wait();
        return "年龄是："+age;
    }
}
