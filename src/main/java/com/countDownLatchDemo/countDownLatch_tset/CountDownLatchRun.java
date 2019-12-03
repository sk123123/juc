package com.countDownLatchDemo.countDownLatch_tset;

import java.util.concurrent.CountDownLatch;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/19 10:26
 */
public class CountDownLatchRun {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        CountDownLatchThread [] array = new CountDownLatchThread[Integer.parseInt(""+
        countDownLatch.getCount())];
        for(int i = 0; i<array.length;i++){
            array[i] = new CountDownLatchThread(countDownLatch);
            array[i].start();
        }
        Thread.sleep(1000);
        countDownLatch.await();
        System.out.println("所有线程同步完成");
    }
}
