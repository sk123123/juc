package com.java8;

import java.util.Arrays;
import java.util.Comparator;

public class LambdaDemo implements Runnable{
    public static void main(String[] args) {
        String[] strArr = { "abc", "cd", "abce", "a" };
        Arrays.sort(strArr, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s2.length(), s1.length());
            }
        });
        for (String s : strArr) {
            System.out.println(s);
        }
        System.out.println("---------------------");
    /**Lambda**/
    Arrays.sort(strArr,(s1, s2)->Integer.compare(s2.length(), s1.length()));

    new Thread(()->System.out.println("hell wprld")).start();
    }

    @Override
    public void run() {
        new Thread(()->System.out.println("hell wprld")).start();
    }
}
