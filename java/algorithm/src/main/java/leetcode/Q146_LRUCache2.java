package leetcode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于 LinkedHashMap 的 LRUCache 实现
 * @author zhangxiaofan 2021/01/04-15:15
 */
public class Q146_LRUCache2 {
    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int cacheSize;

        LRUCache(int cacheSize) {
            super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
            this.cacheSize = cacheSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > cacheSize;
        }
    }
}
