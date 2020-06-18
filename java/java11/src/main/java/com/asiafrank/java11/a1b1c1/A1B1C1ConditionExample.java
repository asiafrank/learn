package com.asiafrank.java11.a1b1c1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：
 * 开启两个线程
 * 线程1：依次打印 A，B，C，D...Z
 * 线程2：依次打印 1，2，3，4...26
 * 要求两线程打印的最终结果为：A1B2C3...Z26
 * <p>
 * 使用 lock condition 来做，该做法由于需要嵌套 lock try finally block 所以不太优雅。
 *
 * @author zhangxiaofan 2020/06/18-10:11
 */
public class A1B1C1ConditionExample {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        // t1 唤醒 t2
        Condition c_1_2 = lock.newCondition();
        // t1 唤醒 t1
        Condition c_2_1 = lock.newCondition();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                lock.lock();
                try {
                    System.out.print((char) ('A' + i));
                    c_1_2.signalAll(); // 唤醒 t2
                    c_2_1.await(); // 等待 t2 来唤醒 t1
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            lock.lock();
            try {
                c_1_2.signalAll();
            } finally {
                lock.unlock();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                lock.lock();
                try {
                    System.out.print(i + 1);
                    c_2_1.signalAll(); // 唤醒 t1
                    c_1_2.await(); // 等待 t1 来唤醒 t2
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            lock.lock();
            try {
                c_2_1.signalAll();
            } finally {
                lock.unlock();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
