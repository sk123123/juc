package com.semaphoreDemo;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/9 23:13
 */
public class ThreadB extends Thread {
    private SemaPhoreDemoSyn semaPhoreDemoSyn;
    public ThreadB(SemaPhoreDemoSyn semaPhoreDemoSyn){
        super();
        this.semaPhoreDemoSyn = semaPhoreDemoSyn;
    }

    @Override
    public void run() {
        semaPhoreDemoSyn.testMethod();
    }
}
