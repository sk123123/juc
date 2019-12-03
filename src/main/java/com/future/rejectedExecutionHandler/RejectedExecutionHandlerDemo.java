package com.future.rejectedExecutionHandler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/24 9:37
 */
public class RejectedExecutionHandlerDemo implements RejectedExecutionHandler {
    /**
     * 自定义拒绝策略
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString()+"被拒绝！");
    }
}
