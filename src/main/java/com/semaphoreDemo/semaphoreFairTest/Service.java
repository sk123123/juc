package com.semaphoreDemo.semaphoreFairTest;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/18 20:50
 */
public class Service extends Thread{
    private MyService myService;
    public Service (MyService myService){
        super();
        this.myService = myService;
    }

    @Override
    public void run() {
        System.out.println("ThreadName :"+this.getName()+"启动");
        myService.testMethod();
    }
}
