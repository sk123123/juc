package com.semaphoreDemo.semaphorePC;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/18 22:12
 */
public class RepastRun {
    public static void main(String[] args) throws InterruptedException {
        RepastService repastService = new RepastService();
        ThreadA[] threadA = new ThreadA[30];
        ThreadB[] threadB = new ThreadB[30];
        for(int i = 0; i<30;i++){
            threadA[i] = new ThreadA(repastService);
            threadB[i] = new ThreadB(repastService);
        }
        Thread.sleep(0);
        for(int i = 0; i<30;i++){
            threadA[i].start();
            threadB[i].start();
        }
    }
}
