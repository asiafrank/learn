package com.asiafrank.java11.a1b1c1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 题目：
 * 开启两个线程
 * 线程1：依次打印 A，B，C，D...Z
 * 线程2：依次打印 1，2，3，4...26
 * 要求两线程打印的最终结果为：A1B2C3...Z26
 *
 * 模拟 channel 的形式做线程间通信
 *
 * @author zhangxiaofan 2020/06/18-09:28
 */
public class A1B1C1ChannelExample {
    /*
    capacity 必须为 1，因为大于 1 时，put 方法不阻塞，使得 t2 向 channel_2_1 发了 2 条消息，t1 会直接打印 2 次
     */

    // 线程 1 向 2 发消息
    private static final BlockingQueue<Boolean> channel_1_2 = new ArrayBlockingQueue<>(1);

    // 线程 2 向 1 发消息
    private static final BlockingQueue<Boolean> channel_2_1 = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(()->{
            // 打印 26 字母
            for (int i = 0; i < 26; i++) {
                System.out.print((char)('A' + i));
                try {
                    channel_1_2.put(Boolean.TRUE); // 向 t2 发消息，让 t2 运行
                    channel_2_1.take(); // 从 t2 那儿收到消息，才能运行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 26; i++) {
                System.out.print(i + 1);
                try {
                    channel_2_1.put(Boolean.TRUE); // 向 t1 发消息，让 t1 运行
                    channel_1_2.take(); // 从 t1 那儿收到消息，才能运行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println("\ndelta=" + delta + "ms");
    }
}
