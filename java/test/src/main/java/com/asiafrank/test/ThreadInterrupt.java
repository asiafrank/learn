package com.asiafrank.test;

public class ThreadInterrupt {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(()->{
            try {
                Thread.sleep(100000L);
            } catch (InterruptedException e) {
                System.out.println("Thread t throw interrupt");
            } finally {
                System.out.println("Thread final");
            }
        });
        t.start();

        Thread.sleep(5000L);
        t.interrupt();
        System.out.println("main end");
        Thread.sleep(10000L);
    }
}
