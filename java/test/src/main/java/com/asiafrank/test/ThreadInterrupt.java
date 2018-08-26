package com.asiafrank.test;

/**
 * Java里一个线程调用了Thread.interrupt()到底意味着什么？ - Intopass的回答 - 知乎
 * https://www.zhihu.com/question/41048032/answer/89431513
 *
 * http://www.fanyilun.me/2016/11/19/Thread.interrupt()%E7%9B%B8%E5%85%B3%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90/
 *
 * 源码见： os_windows.cpp,os_posix.cpp,park.cpp  重要方法 os::PlatformEvent::unpark()
 * 然后下面方法的 native 代码会抛出 InterruptedException (hotspot 源码搜索 “java_lang_InterruptedException()” 就明白了)
 * @see     java.lang.Object#wait()
 * @see     java.lang.Object#wait(long)
 * @see     java.lang.Object#wait(long, int)
 * @see     java.lang.Thread#sleep(long)
 * @see     java.lang.Thread#interrupt()
 * @see     java.lang.Thread#interrupted()
 */
public class ThreadInterrupt {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(()->{
            try {
                Thread.sleep(100000L);
            } catch (InterruptedException e) {
                System.out.println("Thread t throw interrupt");
            } finally {
                System.out.println("Thread final");
            }
        });
        t.start();

        Thread.sleep(5000L);
        t.interrupt();
        System.out.println("main end");
        Thread.sleep(10000L);
    }
}
