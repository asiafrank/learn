package com.asiafrank.learn.concurrency.exercise;

/**
 * Thinking in Java - page 1120
 *
 * Created by Xiaofan Zhang on 29/2/2016.
 */
public class Exercise1 {
    public static void main(String[] args) {
        System.out.println("Main start!");
        for (int i = 0; i < 10; i++) {
            new Thread(new ThreadA()).start();
        }
        System.out.println("Main done!");
    }
}

class ThreadA implements Runnable {
    private static int count = 0;
    private int id = ++count;

    public ThreadA() {
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