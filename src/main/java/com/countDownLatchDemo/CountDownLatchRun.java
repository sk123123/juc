package com.countDownLatchDemo;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/19 9:54
 */
public class CountDownLatchRun {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchService countDownLatchService = new CountDownLatchService();
        CountDownLatchThread countDownLatchThread = new CountDownLatchThread(countDownLatchService);
        CountDownLatchThread[] arrays = new CountDownLatchThread[20];
        for(int i = 0;i<arrays.length;i++){
            arrays[i] = new CountDownLatchThread(countDownLatchService);
            arrays[i].setName("线程"+(i+1));
            arrays[i].start();
//            arrays[i].join();
        }
//        countDownLatchThread.start();
        Thread.sleep(1000);
        countDownLatchService.downMethod();
    }
}
