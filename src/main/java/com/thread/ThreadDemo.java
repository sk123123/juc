package com.thread;

public class ThreadDemo {
    public static void main(String[] args) {
        final int[] sum = {0};
        Thread thread = new Thread(){
            @Override
            public void run() {
                for(int i =0;i<100;i++){
                    i += i ;

                    System.out.println(i);
                }
            }
        };
        thread.start();
        thread.setDaemon(true);
    }
}
