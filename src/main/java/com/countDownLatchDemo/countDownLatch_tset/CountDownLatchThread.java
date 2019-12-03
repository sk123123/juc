package com.countDownLatchDemo.countDownLatch_tset;

import java.util.concurrent.CountDownLatch;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/19 10:24
 * 测试多线程同步完成
 */
public class CountDownLatchThread extends Thread {
    private CountDownLatch countDownLatch;

    public CountDownLatchThread(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        countDownLatch.countDown();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
