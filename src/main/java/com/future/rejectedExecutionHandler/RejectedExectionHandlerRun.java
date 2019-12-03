package com.future.rejectedExecutionHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/24 9:43
 */
public class RejectedExectionHandlerRun {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//        ExecutorService service = Executors.newCachedThreadPool();
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
        executor.setRejectedExecutionHandler(new RejectedExecutionHandlerDemo());
        executor.submit(new MyRunnable("A"));
        executor.submit(new MyRunnable("B"));
        executor.shutdown();
        executor.submit(new MyRunnable("D"));
    }
}
