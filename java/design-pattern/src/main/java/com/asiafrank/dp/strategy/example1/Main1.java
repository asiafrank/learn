package com.asiafrank.dp.strategy.example1;

/**
 * @author user created at 6/13/2017.
 */
public class Main1 {
    public static void main(String[] args) {
        Strategy1 a = new StrategyImplA();
        Context1 ca = new Context1(a);
        ca.doMethod();

        Strategy1 b = new StrategyImplB();
        Context1 cb = new Context1(b);
        cb.doMethod();

        Strategy1 c = new StrategyImplC();
        Context1 cc = new Context1(c);
        cc.doMethod();
    }
}
