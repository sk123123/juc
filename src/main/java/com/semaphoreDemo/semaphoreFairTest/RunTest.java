package com.semaphoreDemo.semaphoreFairTest;

/**
 * @author sunkai
 * @version 1.0
 * @date 2019/11/18 20:54
 */
public class RunTest {
    public static void main(String[] args) {
        MyService myService = new MyService();
        Service service = new Service(myService);
        service.start();
        Service[] serviceArray = new Service[4];
        for(int i=0;i<serviceArray.length;i++){
            serviceArray[i] = new Service(myService);
            serviceArray[i].start();
        }
    }
}
