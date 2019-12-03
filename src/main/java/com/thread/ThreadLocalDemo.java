package com.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 当使用SimpleDateFormat时因为线程不安全
 * 处理方式：synchronized、加锁ReentrantLock、为每个线程添加副本变量ThreadLocal
 */
public class ThreadLocalDemo {
    private  static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>();
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    public static class ParseDate implements Runnable{
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                synchronized (ParseDate.class){
                    if(sdf.get()== null){
                        sdf.set(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss"));
                    }
                    Date date = sdf.get().parse("2019-11-25 09:36:30"+i%60);
                    System.out.println(i+":"+date);
                    condition.signalAll();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i =0;i<10;i++){
            executorService.execute(new ParseDate(i));
        }
    }
}
