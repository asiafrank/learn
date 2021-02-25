package com.asiafrank.labuladong.datastruct;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangxiaofan 2021/02/25-09:31
 */
public class LRUCache2 extends LinkedHashMap<Integer, Integer> {

    private final int capacity;

    public LRUCache2(int initialCapacity) {
        super(16, 0.75f, true);
        capacity = initialCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
