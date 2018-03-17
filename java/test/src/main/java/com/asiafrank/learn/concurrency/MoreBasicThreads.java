package com.asiafrank.learn.concurrency;

/**
 * Thinking in Java - page 1117
 *
 * Created by Xiaofan Zhang on 27/2/2016.
 */
public class MoreBasicThreads {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for LiftOff");
    }
}
