package com.countDownLatchDemo;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/19 9:52
 */
public class CountDownLatchThread extends Thread {
    private CountDownLatchService countDownLatchService ;
    public CountDownLatchThread(CountDownLatchService countDownLatchService){
        super();
        this.countDownLatchService = countDownLatchService;
    }
    @Override
    public void run() {
        try {
            countDownLatchService.testMethod();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
