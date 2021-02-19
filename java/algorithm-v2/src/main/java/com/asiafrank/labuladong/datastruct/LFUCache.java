package com.asiafrank.labuladong.datastruct;

import java.util.*;

/**
 * LFU 算法
 * https://mp.weixin.qq.com/s/oXv03m1J8TwtHwMJEZ1ApQ
 * @author zhangxiaofan 2021/02/19-10:23
 */
public class LFUCache {

    int capacity;

    Map<Integer, Entry> keyToValue = new HashMap<>();

    Map<Integer, LinkedHashSet<Entry>> freqToEntry = new HashMap<>();

    int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Entry e = keyToValue.get(key);
        if (e == null) {
            return -1;
        }

        increaseFreq(e);
        return e.val;
    }

    public void put(int key, int value) {
        Entry e = keyToValue.get(key);
        if (e == null) {
            e = new Entry();
            e.key = key;
            e.val = value;
            e.freq = 1;
            minFreq = 1;

            if (keyToValue.size() == capacity) { // 满了，淘汰最少频率的
                removeMinFreq();
            }

            keyToValue.put(key, e);
            freqToEntry.putIfAbsent(1, new LinkedHashSet<>());
            freqToEntry.get(1).add(e);
            return;
        }

        increaseFreq(e);
    }

    private void increaseFreq(Entry e) {
        // 如果是最小的
        if (e.freq == minFreq) {
            minFreq++;
        }

        LinkedHashSet<Entry> keySet = freqToEntry.get(e.freq);
        keySet.remove(e);
        if (keySet.isEmpty()) {
            freqToEntry.remove(e.freq);
        }

        e.freq++;
        freqToEntry.putIfAbsent(e.freq, new LinkedHashSet<>());
        freqToEntry.get(e.freq).add(e);
    }

    private void removeMinFreq() {
        LinkedHashSet<Entry> keySet = freqToEntry.get(minFreq);
        Entry n = keySet.iterator().next();
        keySet.remove(n);

        if (keySet.isEmpty()) {
            freqToEntry.remove(minFreq);
        }

        keyToValue.remove(n.key);
    }

    private static class Entry {
        int key;
        int val;
        int freq; // 频率, 不参与 hash

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return key == entry.key && val == entry.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, val);
        }
    }

    public static void main(String[] args) {
        // 构造一个容量为 2 的 LFU 缓存
        LFUCache cache = new LFUCache(2);

        // 插入两对 (key, val)，对应的 freq 为 1
        cache.put(1, 10);
        cache.put(2, 20);

        // 查询 key 为 1 对应的 val
        // 返回 10，同时键 1 对应的 freq 变为 2
        cache.get(1);

        // 容量已满，淘汰 freq 最小的键 2
        // 插入键值对 (3, 30)，对应的 freq 为 1
        cache.put(3, 30);

        // 键 2 已经被淘汰删除，返回 -1
        cache.get(2);
    }
}
