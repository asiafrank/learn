package com.asiafrank.se.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    int a = 0;
    ReentrantLock lock = new ReentrantLock();

    public void write() {
        lock.lock();
        try {
            a++;
        } finally {
            lock.unlock();
        }
    }

    public void read() {
        lock.lock();
        try {
            int i = a;
            // do something
        } finally {
            lock.unlock();
        }
    }
}
