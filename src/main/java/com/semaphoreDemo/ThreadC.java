package com.semaphoreDemo;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/9 23:14
 */
public class ThreadC extends Thread {
    private SemaPhoreDemoSyn semaPhoreDemoSyn;
    public ThreadC(SemaPhoreDemoSyn semaPhoreDemoSyn){
        super();
        this.semaPhoreDemoSyn = semaPhoreDemoSyn;
    }

    @Override
    public void run() {
        semaPhoreDemoSyn.testMethod();
    }
}
