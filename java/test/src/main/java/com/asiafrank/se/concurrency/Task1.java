package com.asiafrank.se.concurrency;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 随机生成10个字符的字符串
 * @author dream-victor
 *
 */
public class Task1 implements Callable<String> {

    @Override
    public String call() throws Exception {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            ExecutorService service = Executors.newFixedThreadPool(4);
            service.submit(new Task1());
            service.submit(new Task1());
            service.submit(new LongTask());
            service.submit(new Task1());

            service.shutdown();

            while (!service.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("线程池没有关闭");
            }
            System.out.println("线程池已经关闭");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Main exit");
        }
    }
}