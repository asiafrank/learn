package com.asiafrank.java11.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Cache 简单实现: 使用 FutureTask 优化，减少多个线程同时进入 getFromDB() 的调用。
 * <ol>
 * <li>使用 ConcurrentHashMap 降低锁力度，增加并发性</li>
 * <li>getFromDB 涉及到网络 IO，众所周知，IO 操作很费时间。FutureTask 对象的创建消耗比 IO 小得多</li>
 * <li>多创建的对象变成FutureTask 。FutureTask的创建消耗可控，能在栈上分配，多创建的FutureTask能马上回收。</li>
 * <li>ConcurrentHashMap的putIfAbsent里有CAS的控制。返回为 null，证明已经将 FutureTask 对象 put 进去了，
 * 调用 run() 方法执行 getFromDB()。FutureTask 本身使用 CAS 保证了 Callable 只调用一次，即使同时调用 run() 多次，
 * getFromDB 也不会多调用一次浪费资源</li>
 * </ol>
 *
 * @author zhangxf created at 2/11/2019.
 */
public class CacheExample2<K,V> {

    private final Map<K, FutureTask<V>> cache = new ConcurrentHashMap<>();

    public V get(K key) throws ExecutionException, InterruptedException {
        var ft = cache.get(key);
        if (ft != null)
            return ft.get();

        ft = new FutureTask<>(() -> getFromDB(key));
        var prevFT = cache.putIfAbsent(key, ft);
        if (prevFT == null)
            ft.run();
        else
            ft = prevFT;
        return ft.get();
    }

    private V getFromDB(K key) {
        // 从数据库中获取数据
        return null;
    }
}
