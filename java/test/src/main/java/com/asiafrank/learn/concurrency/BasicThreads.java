package com.asiafrank.learn.concurrency;

/**
 * Thinking in Java - page 1118
 * Demonstration of the Runnable interface
 *
 * Created by Xiaofan Zhang on 27/2/2016.
 */
public class BasicThreads {
    public static void main(String[] args) {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("Waiting for LiftOff");
    }
}
