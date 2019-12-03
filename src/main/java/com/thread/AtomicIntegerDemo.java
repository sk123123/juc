package com.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 比较CAS中AtomicInteger原子性
 */
public class AtomicIntegerDemo {
    static AtomicInteger atomicInteger = new AtomicInteger();
    static AtomicReference<Integer> reference = new AtomicReference<>();
    public static  class MyThread implements Runnable{
        @Override
        public void run() {
            for(int i =0;i<1000;i++){
                reference.getAndSet(i);
                atomicInteger.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        int startTime = (int) System.currentTimeMillis();
        System.out.println("开始时间："+System.currentTimeMillis());
        for(int i =0;i<10;i++){
            threads[i] = new Thread(new MyThread());
        }
        for(int i =0;i<10;i++){
            threads[i].start();
        }
        for(int i =0;i<10;i++){
            threads[i].join();
        }
        System.out.println(atomicInteger);
        int endTime = (int) System.currentTimeMillis();
        System.out.println("结束时间："+System.currentTimeMillis());
        System.out.println("总用时时间："+(endTime-startTime));
    }
}
