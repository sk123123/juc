package com.semaphoreDemo.semaphoreFairTest;

import java.util.concurrent.Semaphore;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/18 19:28
 * 非公平信号量测试
 */
public class MyService {
    private boolean isFair = false;
    private Semaphore semaphore = new Semaphore(1,isFair);

    public void testMethod(){
        try {
            semaphore.acquire();
            System.out.println("ThreadName :"+Thread.currentThread().getName()
                    );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }
}
