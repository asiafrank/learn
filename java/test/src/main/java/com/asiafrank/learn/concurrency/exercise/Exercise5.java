package com.asiafrank.learn.concurrency.exercise;

import com.asiafrank.learn.generics.Fibonacci;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Thinking in Java - page 1125
 *
 * Created by Xiaofan Zhang on 29/2/2016.
 */
public class Exercise5 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(5);
//        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<Integer>> results = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            results.add(exec.submit(new CallableA(i)));
        }

        for (Future<Integer> fi : results) {
            try {
                System.out.print(fi.get() + " ");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return;
            } finally {
                exec.shutdown();
            }
        }
    }
}

class CallableA implements Callable<Integer> {
    private int steps;

    public CallableA(int steps) {
        if (steps <= 0) {
            steps = 1;
        }
        this.steps = steps;
    }

    @Override
    public Integer call() throws Exception {
        Fibonacci f = new Fibonacci();
        for (int i = 0; i < steps; i++) {
            f.next();
        }
        return f.next();
    }
}
