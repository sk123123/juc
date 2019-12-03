package com.semaphoreDemo.semaphorePool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/18 21:16
 * 创建字符串池
 */
public class SemaPhorePoolTest {
    //许可证数
    private int semaphorePermits = 5;
    private int poolSize = 3;
    private List<String> list = new ArrayList<String>();
    private Semaphore semaphore = new Semaphore(semaphorePermits);
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    public SemaPhorePoolTest(){
        super();
        for(int i=0;i<poolSize;i++){
            list.add("Java"+(i+1));
        }
    }
    public String get(){
        String s = null;
        try {
            semaphore.acquire();
            reentrantLock.lock();
            while(list.size()==0){
                condition.await();
            }
            s = list.remove(0);
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void put(String sval){
        reentrantLock.lock();
        list.add(sval);
        condition.signalAll();
        reentrantLock.unlock();
        semaphore.release();

    }
}
