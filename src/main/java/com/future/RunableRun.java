package com.future;

import java.util.concurrent.*;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/24 9:22
 */
public class RunableRun {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("打印消息");
            }
        };
        ExecutorService executor = Executors.newCachedThreadPool();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                3,
                5L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque());
        Future future = threadPoolExecutor.submit(runnable);
//                executor.submit(runnable);
        System.out.println(future.get()+"//"+future.isDone());
    }
}
