package com.asiafrank.dp.strategy.example1;

/**
 * @author user created at 6/13/2017.
 */
public class Context1 {
    private final Strategy1 s;

    public Context1(Strategy1 s) {
        this.s = s;
    }

    public void doMethod() {
        s.method();
    }
}
