package com.asiafrank.se.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class SynchronizedTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        final Data data = new Data();
        pool.submit(new Runnable() {
            @Override
            public void run() {
                data.updateWithStampedLock(3, 4);
            }
        });

        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(data.toStringWithStampedLock());
            }
        });

        pool.shutdown();
        try {
            while (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("waiting to shutdown");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Data {
        private int x = 1;
        private int y = 2;
        private final Object mutex;
        private final ReentrantLock reentrantLock = new ReentrantLock();
        private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private final Lock read = readWriteLock.readLock();
        private final Lock write = readWriteLock.writeLock();
        private final StampedLock stampedLock = new StampedLock();

        public Data() {
            mutex = this;
        }

        /*
         * ------------------------------------------------------------
         * synchronized
         * ------------------------------------------------------------
         */

        public Data updateWithSync(int x, int y) {
            synchronized (mutex) {
                System.out.println("updateWithSync start");
                try {
                    Thread.currentThread().sleep(5000L);
                    this.x = x;
                    this.y = y;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("updateWithSync end");
            }
            return this;
        }

        public String toStringWithSync() {
            synchronized (mutex) {
                return "toStringWithSync Data{x=" + x + ", y=" + y + '}';
            }
        }

        /*
         * ------------------------------------------------------------
         * ReentrantLock
         * ------------------------------------------------------------
         */

        public Data updateWithReentrantLock(int x, int y) {
            reentrantLock.lock();
            try {
                System.out.println("updateWithReentrantLock start");
                try {
                    Thread.currentThread().sleep(5000L);
                    this.x = x;
                    this.y = y;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("updateWithReentrantLock end");
            } finally {
                reentrantLock.unlock();
            }
            return this;
        }

        public String toStringWithReentrantLock() {
            reentrantLock.lock();
            try {
                return "toStringWithReentrantLock Data{x=" + x + ", y=" + y + '}';
            } finally {
                reentrantLock.unlock();
            }
        }

        /*
         * ------------------------------------------------------------
         * ReentrantReadWriteLock
         * ------------------------------------------------------------
         */

        public Data updateWithReentrantReadWriteLock(int x, int y) {
            write.lock();
            try {
                System.out.println("updateWithReentrantReadWriteLock start");
                try {
                    Thread.currentThread().sleep(5000L);
                    this.x = x;
                    this.y = y;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("updateWithReentrantReadWriteLock end");
            } finally {
                write.unlock();
            }
            return this;
        }

        public String toStringWithReentrantReadWriteLock() {
            read.lock();
            try {
                return "toStringWithReentrantReadWriteLock Data{x=" + x + ", y=" + y + '}';
            } finally {
                read.unlock();
            }
        }

        /*
         * ------------------------------------------------------------
         * StampedLock
         * ------------------------------------------------------------
         */

        public Data updateWithStampedLock(int x, int y) {
            long stamp = stampedLock.writeLock();
            try {
                System.out.println("updateWithStampedLock start");
                try {
                    Thread.currentThread().sleep(5000L);
                    this.x = x;
                    this.y = y;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("updateWithStampedLock end");
            } finally {
                stampedLock.unlockWrite(stamp);
            }
            return this;
        }

        public String toStringWithStampedLock() {
            long stamp = stampedLock.tryOptimisticRead();
            int localX = x, localY = y;
            String str = "-Data{x=" + localX + ", y=" + localY + '}';
            if (!stampedLock.validate(stamp)) {
                stamp = stampedLock.readLock();
                try {
                    str = "toStringWithStampedLock Data{x=" + x + ", y=" + y + '}';
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            return str;
        }
    }
}
