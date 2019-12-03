package com.semaphoreDemo.semaphorePC;


/**
 * @author sunkai
 * @version 1.0
 */
public class ThreadA extends Thread {
    private RepastService semaPhoreDemoSyn;
    public ThreadA(RepastService semaPhoreDemoSyn){
        super();
        this.semaPhoreDemoSyn = semaPhoreDemoSyn;
    }

    @Override
    public void run() {
        semaPhoreDemoSyn.set();
    }
}
