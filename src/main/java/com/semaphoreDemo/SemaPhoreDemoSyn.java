package com.semaphoreDemo;

import java.util.concurrent.Semaphore;

/**
 * @author sunkai
 * @version 1.0
 * semaphore同步性
 * @date 2019/11/9 23:03
 */
public class SemaPhoreDemoSyn {
    //permits设置许可树
    private Semaphore semaphore = new Semaphore(3);
    public void testMethod(){
        try {
            semaphore.acquire();
            System.out.println("当前可用许可数"+semaphore.availablePermits());
//            semaphore.acquireUninterruptibly();
            System.out.println(Thread.currentThread().getName()
            +"开始时间:"+System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName()
                    +"结束时间:"+System.currentTimeMillis());
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SemaPhoreDemoSyn semaPhoreDemoSyn = new SemaPhoreDemoSyn();
        ThreadA threadA = new ThreadA(semaPhoreDemoSyn);
        threadA.setName("A");
        ThreadB threadB = new ThreadB(semaPhoreDemoSyn);
        threadB.setName("B");
        ThreadC threadC = new ThreadC(semaPhoreDemoSyn);
        threadC.setName("C");
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
