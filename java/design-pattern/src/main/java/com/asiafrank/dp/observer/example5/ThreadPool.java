package com.asiafrank.dp.observer.example5;

import java.util.concurrent.*;

/**
 * Created by zhangxf on 11/21/2016.
 */
public final class ThreadPool {
    private static final ExecutorService exe = new ThreadPoolExecutor(1, 1, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));

    public static void execute(Runnable task) {
        exe.submit(task);
    }

    public static void shutdown() {
        exe.shutdown();
        try {
            while (!exe.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Waiting to terminate.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
