package com.asiafrank.learn.concurrency;

/**
 * Thinking in Java - page 1118
 *
 * Created by Xiaofan Zhang on 27/2/2016.
 */
public class MainThread {
    public static void main(String[] args) {
        LiftOff launch = new LiftOff();
        launch.run();
    }
}
