package com.semaphoreDemo.semaphorePC;

/**
 * @author sunkai
 * @version 1.0
 */
public class ThreadB extends Thread {
    private RepastService semaPhoreDemoSyn;
    public ThreadB(RepastService semaPhoreDemoSyn){
        super();
        this.semaPhoreDemoSyn = semaPhoreDemoSyn;
    }

    @Override
    public void run() {
        semaPhoreDemoSyn.get();
    }
}
