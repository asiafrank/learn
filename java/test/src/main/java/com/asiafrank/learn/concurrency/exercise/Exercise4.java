package com.asiafrank.learn.concurrency.exercise;

import com.asiafrank.learn.generics.Fibonacci;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thinking in Java - page 1124
 *
 * Created by Xiaofan Zhang on 29/2/2016.
 */
public class Exercise4 {
    public static void main(String[] args) {
        System.out.println("Main start!");
//        ExecutorService exec = Executors.newSingleThreadExecutor();
        ExecutorService exec = Executors.newFixedThreadPool(10);
//        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 18; i++) {
            exec.execute(new ThreadD(i));
        }
        exec.shutdown();
        System.out.println("Main done!");
    }
}

class ThreadD implements Runnable {
    private int steps;

    public ThreadD(int steps) {
        if (steps <= 0) {
            steps = 1;
        }
        this.steps = steps;
    }

    @Override
    public void run() {
        Fibonacci f = new Fibonacci();
        for (int i = 0; i < steps; i++) {
            f.next();
        }
        System.out.print(f.next() + " ");
    }
}