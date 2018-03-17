package com.asiafrank.learn.generics;

import net.mindview.util.Generator;

/**
 * Thinking in Java - page 630
 *
 * Created by Xiaofan Zhang on 29/2/2016.
 */
public class Fibonacci implements Generator<Integer> {
    private int count = 0;

    @Override
    public Integer next() {
        return fib(count++);
    }

    private int fib(int n) {
        if (n < 2) return 1;
        return fib(n - 2) + fib(n - 1);
    }

    public static void main(String[] args) {
        Fibonacci f = new Fibonacci();
        for (int i = 0; i < 18; i++) {
            System.out.print(f.next() + " ");
        }
    }
}
