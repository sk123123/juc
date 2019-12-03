package com.future.completionService;

import java.util.concurrent.Callable;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/24 9:59
 */
public class CompletionServiceRunnable implements Callable {
    private String username;
    private long timeUnit;

    public CompletionServiceRunnable(String username,long timeUnit){
        this.username = username;
        this.timeUnit = timeUnit;
    }
    @Override
    public String call() throws Exception {
        System.out.println(username);
        Thread.sleep(1000);
        return username;
    }
}
