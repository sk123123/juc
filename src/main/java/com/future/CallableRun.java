package com.future;

import java.util.concurrent.*;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/24 9:14
 */
public class CallableRun {
    public static void main(String[] args) {
        CallableDemo callableDemo = new CallableDemo(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                3,
                5L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque());
        Future future = threadPoolExecutor.submit(callableDemo);
        try {
            //get方法具有阻塞性
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
