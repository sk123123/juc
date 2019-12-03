package com.semaphoreDemo.semaphorePool;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/18 21:28
 */
public class ThreadTest extends Thread{
    private SemaPhorePoolTest semaPhorePoolTest;
    public ThreadTest(SemaPhorePoolTest semaPhorePoolTest){
        this.semaPhorePoolTest = semaPhorePoolTest;
    }

    @Override
    public void run() {
        for(int i= 1;i<Integer.MAX_VALUE;i++){
            String getString = semaPhorePoolTest.get();
            System.out.println(Thread.currentThread().getName()+"取得值"+getString);
            semaPhorePoolTest.put(getString);
        }
    }
}
