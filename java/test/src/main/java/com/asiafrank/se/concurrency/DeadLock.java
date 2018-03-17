package com.asiafrank.se.concurrency;

/**
 * 死锁
 */
public class DeadLock {
    private final Object o1 = new Object();
    private final Object o2 = new Object();

    public static void main(String[] args) {
        final DeadLock dl = new DeadLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dl.oneTwo();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                dl.twoOne();
            }
        }).start();
    }

    public void oneTwo() {
        synchronized (o1) {
            // for another thread locking o2
            try {
                Thread.currentThread().sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                System.out.println("locking");
            }
        }
    }

    public void twoOne() {
        synchronized (o2) {
            // for another thread locking o1
            try {
                Thread.currentThread().sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o1) {
                System.out.println("locking");
            }
        }
    }
}
