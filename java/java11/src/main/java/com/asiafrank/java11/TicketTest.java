package com.asiafrank.java11;

import java.util.concurrent.TimeUnit;

public class TicketTest implements Runnable {

    private int tickets = 1000;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            testSell();

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void testSell() {
        if (tickets > 0) {
            System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
        }
    }

    public static void main(String[] args) {
        TicketTest t = new TicketTest();
        for (int i = 0; i < 5; i++) {
            Thread th = new Thread(t, "窗口" + i);
            th.start();
        }
    }
}
