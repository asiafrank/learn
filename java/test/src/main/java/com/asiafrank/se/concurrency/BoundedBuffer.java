package com.asiafrank.se.concurrency;

import java.util.concurrent.Semaphore;

@ThreadSafe
public class BoundedBuffer<E> {
    private final Semaphore avaliableItems, avaliableSpaces;
    @GuardedBy("this") private final E[] items;
    @GuardedBy("this") private int putPosition = 0, takePosition = 0;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int capacity) {
        avaliableItems = new Semaphore(0);
        avaliableSpaces = new Semaphore(capacity);
        items = (E[])new Object[capacity];
    }

    public boolean isEmpty() {
        return avaliableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return avaliableSpaces.availablePermits() == 0;
    }

    public void put(E item) throws InterruptedException {
        avaliableSpaces.acquire();
        doInsert(item);
        avaliableItems.release();
    }

    public E take() throws InterruptedException {
        avaliableItems.acquire();
        E item = doExtract();
        avaliableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E item) {
        int i = putPosition;
        items[i] = item;
        putPosition = (++i == items.length ? 0 : i);
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E item = items[i];
        items[i] = null;
        takePosition = (++i == items.length ? 0 : i);
        return item;
    }
}
