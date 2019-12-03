package com.future.rejectedExecutionHandler;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/24 9:41
 */
public class MyRunnable implements Runnable {
    private String username;

    public MyRunnable(String username){
        this.username = username;
    }
    @Override
    public void run() {
        System.out.println(username+"运行中");
    }
}
