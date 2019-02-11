package com.asiafrank.java11.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache 简单实现：使用 ConcurrentHashMap 做简单并发控制
 *
 * @author zhangxf created at 2/11/2019.
 */
public class CacheExample <K,V> {

    private final Map<K, V> cache = new ConcurrentHashMap<>();

    public V get(K key) {
        V v = cache.get(key);
        if (v != null)
            return v;

        v = getFromDB(key);
        cache.put(key, v);
        return v;
    }

    private V getFromDB(K key) {
        // 从数据库中获取数据
        return null;
    }
}
