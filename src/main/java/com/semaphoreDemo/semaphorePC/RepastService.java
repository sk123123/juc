package com.semaphoreDemo.semaphorePC;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/18 21:48
 * semaphore实现生产者消费者模式
 */
public class RepastService {
    volatile private Semaphore semaphoreProduce = new Semaphore(10);
    volatile private Semaphore semaphoreConsumer = new Semaphore(20);
    volatile private ReentrantLock reentrantLock = new ReentrantLock();
    volatile private Condition conditionProduce = reentrantLock.newCondition();
    volatile private Condition conditionConsumer = reentrantLock.newCondition();
    //最大存放数
    volatile private Object[] produceMax = new Object[4];

    public boolean isEmpty(){
        boolean isEmpty = true;
        for(int i =0;i<produceMax.length;i++){
            if(produceMax.length == 0){
                isEmpty = false;
                break;
            }
        }
        if(isEmpty == true){
            return  true;
        }else{
            return false;
        }
    }

    private boolean isFull(){
        boolean isFull = true;
        for(int i =0;i<produceMax.length;i++){
            if(produceMax.length == 0){
                isFull = false;
                break;
            }
        }
        return isFull;
    }

    public void set(){
        try {
            semaphoreProduce.acquire();
            reentrantLock.lock();
            while(isFull()){
                conditionProduce.await();
            }
            for(int i = 0;i<produceMax.length;i++){
                if(produceMax[i] == null){
                    produceMax[i] = "数据";
                    System.out.println(Thread.currentThread().getName()+
                    "生产"+produceMax[i]);
                    break;
                }
                conditionProduce.signalAll();
                reentrantLock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphoreProduce.release();
        }
    }

    public void get(){
        try {
            semaphoreConsumer.acquire();
            reentrantLock.lock();
            while(isEmpty()){
                conditionConsumer.await();
            }
            for(int i = 0;i<produceMax.length;i++){
                if(produceMax[i] != null){
                    System.out.println(Thread.currentThread().getName()+
                            "消费"+produceMax[i]);
                    produceMax[i] = null;
                    break;
                }
                conditionConsumer.signalAll();
                reentrantLock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphoreProduce.release();
        }
    }
}
