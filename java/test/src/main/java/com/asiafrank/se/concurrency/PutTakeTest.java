package com.asiafrank.se.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> bb;
    private final int nTrials, nPairs;

    PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new BoundedBuffer<>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.barrier = new CyclicBarrier(npairs * 2 + 1);
    }

    public static void main(String[] args) {
        new PutTakeTest(10, 10, 100000).test();
    }

    void test() {
        try {
            for (int i = 0; i < nPairs; ++i) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            barrier.await(); // wait for all thread to be ready
            barrier.await(); // wait for all thread to finish
            System.out.println(putSum.get() == takeSum.get());
            pool.shutdown();
            if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("waiting to stop");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class Producer implements Runnable {
        @Override
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int)System.nanoTime());
                int sum = 0;
                System.out.println("Producer ready");
                barrier.await();

                System.out.println("Producer process");
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
                System.out.println("Producer end");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("Consumer ready");
                barrier.await();
                System.out.println("Consumer process");
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
                System.out.println("Consumer end");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
}
