package com.asiafrank.learn.concurrency.exercise;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thinking in Java - page 1124
 *
 * Created by Xiaofan Zhang on 29/2/2016.
 */
public class Exercise3 {
    public static void main(String[] args) {
        System.out.println("Main start!");
        ExecutorService exec = Executors.newSingleThreadExecutor();
//        ExecutorService exec = Executors.newFixedThreadPool(3);
//        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(new ThreadC());
        }
        exec.shutdown();
        System.out.println("Main done!");
    }
}

class ThreadC implements Runnable {
    private static int count = 0;
    private int id = ++count;

    public ThreadC() {
        System.out.println("Thread[" + id + "] initialized!");
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("Thread[" + id + "]:" + "Hello.." + i);
            Thread.yield();
        }
        System.out.println("Thread[" + id + "] done!");
    }
}