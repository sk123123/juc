package com.countDownLatchDemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/19 9:47
 */
public class CountDownLatchService {
    //计数大小
    /**注意：countDown 执行的次数一定要保证与CountDownLatch初始化的值一致**/
    private CountDownLatch countDownLatch = new CountDownLatch(7);
    private int count = (int) countDownLatch.getCount();
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    public void testMethod() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"开始");
//        reentrantLock.lock();
        countDownLatch.await();
//        condition.await();
        System.out.println(Thread.currentThread().getName()+"结束");
    }

    public void downMethod(){
        System.out.println("X");
        System.out.println("countDownLatch.getCount():"+countDownLatch.getCount());
        for(int i = 0;i<count;i++){
            countDownLatch.countDown();
        }
//        condition.signalAll();
//        reentrantLock.unlock();
    }
}
