package com.asiafrank.learn.concurrency.exercise;

import com.asiafrank.learn.generics.Fibonacci;

/**
 * Thinking in Java - page 1120
 *
 * Created by Xiaofan Zhang on 29/2/2016.
 */
public class Exercise2 {
    public static void main(String[] args) {
        System.out.println("Main Start!");
        for (int i = 0; i < 18; i++) {
            new Thread(new ThreadB(i)).start();
        }
        System.out.println("Main done!");
    }
}

class ThreadB implements Runnable {
    private int steps;

    public ThreadB(int steps) {
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