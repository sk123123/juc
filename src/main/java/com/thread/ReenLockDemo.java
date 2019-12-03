package com.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReenLockDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static class Mythread implements Runnable{
        @Override
        public void run() {
            for(int i = 0; i<1000;i++){
                System.out.println("值是："+(i+=i));
            }
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        try {
            lock.lock();
            int startTime = (int) System.currentTimeMillis();
            System.out.println("开始时间："+System.currentTimeMillis());
            for (int i = 0; i < 10; i++) {
                threads[i] = new Thread(new Mythread());
            }
            for (int i = 0; i < 10; i++) {
                threads[i].start();
            }
            for (int i = 0; i < 10; i++) {
                threads[i].join();
            }
            int endTime = (int) System.currentTimeMillis();
            System.out.println("结束时间："+System.currentTimeMillis());
            System.out.println("总用时时间为："+(endTime-startTime));
            condition.signalAll();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
