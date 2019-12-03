package com.thread;

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeDemo {
    public static void main(String[] args) {
        LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();
        linkedBlockingDeque.add("a");
        System.out.println(linkedBlockingDeque);
    }
}
