package com.asiafrank.java11.a1b1c1;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 三个独立线程，一个线程只打印 X，一个线程只打印 Y，一个线程只打印 Z。
 * 要求，打印序列 XYZXYZ
 * 直到用户输入任意键后，停止
 *
 * 思路：
 * 维护一个 counter 来协同每个线程，counter % 3 == id 则，print 当前值
 * @author zhangxiaofan 2020/08/10-16:12
 */
public class XYZPrint {

    private static class RunPrint implements Runnable {
        private final int id;
        private final char c;

        private final AtomicInteger counter;

        private RunPrint(int id, char c, AtomicInteger counter) {
            this.id = id;
            this.c = c;
            this.counter = counter;
        }

        @Override
        public void run() {
            while (true) {
                int i = counter.get();
                if (i % 3 == id) {
                    System.out.print(c);
                    counter.incrementAndGet(); // 加1，轮到下一个 RunPrint 了
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        final AtomicInteger counter = new AtomicInteger(0);
        RunPrint x = new RunPrint(0, 'X', counter);
        RunPrint y = new RunPrint(1, 'Y', counter);
        RunPrint z = new RunPrint(2, 'Z', counter);

        Thread xThread = new Thread(x);
        Thread yThread = new Thread(y);
        Thread zThread = new Thread(z);

        System.out.println("Press any key to end");
        xThread.start();
        yThread.start();
        zThread.start();

        int read = System.in.read();// 任意键结束
        System.exit(0);
    }
}
