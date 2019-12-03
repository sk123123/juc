package com.semaphoreDemo.semaphorePool;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/18 21:32
 */
public class SemaphoreRunTest {
    public static void main(String[] args) {
        SemaPhorePoolTest semaPhorePoolTest = new SemaPhorePoolTest();
//        ThreadTest threadTest = new ThreadTest(semaPhorePoolTest);
        ThreadTest[] threadTestArrays = new ThreadTest[9];
        for(int i=0;i<threadTestArrays.length;i++){
            threadTestArrays[i] = new ThreadTest(semaPhorePoolTest);
            threadTestArrays[i].start();
        }
//        for(int i=0;i<threadTestArrays.length;i++){
//            threadTestArrays[i].start();
//        }
    }
}
