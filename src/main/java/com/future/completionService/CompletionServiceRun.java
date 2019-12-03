package com.future.completionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/24 10:03
 */
public class CompletionServiceRun {
    public static void main(String[] args) {
        CompletionServiceRunnable completionServiceRunnable = new CompletionServiceRunnable("A",1000);
        CompletionServiceRunnable completionServiceRunnable1 = new CompletionServiceRunnable("B",2000);
        CompletionServiceRunnable completionServiceRunnable2 = new CompletionServiceRunnable("C",3000);
        CompletionServiceRunnable completionServiceRunnable3 = new CompletionServiceRunnable("D",4000);

        List<Callable> list = new ArrayList<Callable>();
        list.add(completionServiceRunnable);
        list.add(completionServiceRunnable1);
        list.add(completionServiceRunnable2);
        list.add(completionServiceRunnable3);

        int size = list.size();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        CompletionService completionService = new ExecutorCompletionService(executor);
        for(int i=0;i<size;i++){
            completionService.submit(list.get(i));
        }

        for(int i=0;i<size;i++){
            System.out.println("等待打印第"+(i+1)+"个返回值");
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}
