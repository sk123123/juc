package com.semaphoreDemo;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/9 23:11
 */
public class ThreadA extends Thread {
    private SemaPhoreDemoSyn semaPhoreDemoSyn;
    public ThreadA(SemaPhoreDemoSyn semaPhoreDemoSyn){
        super();
        this.semaPhoreDemoSyn = semaPhoreDemoSyn;
    }

    @Override
    public void run() {
        semaPhoreDemoSyn.testMethod();
    }
}
