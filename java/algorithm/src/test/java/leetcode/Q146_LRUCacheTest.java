package leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author zhangxiaofan 2020/06/22-10:09
 */
public class Q146_LRUCacheTest {
    @Test
    public void test() {
        Q146_LRUCache.LRUCache cache = new Q146_LRUCache.LRUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        Assertions.assertEquals(1, cache.get(1)); // 返回  1
        cache.put(3,3);    // 该操作会使得关键字 2 作废
        Assertions.assertEquals(-1, cache.get(2));      // 返回 -1 (未找到)
        cache.put(4,4);    // 该操作会使得关键字 1 作废
        Assertions.assertEquals(-1, cache.get(1));      // 返回 -1 (未找到)
        Assertions.assertEquals(3, cache.get(3));      // 返回  3
        Assertions.assertEquals(4, cache.get(4));      // 返回  4
    }

    @Test
    public void test2() {
        //["LRUCache","get","put","get","put","put","get","get"]
        //[[2],[2],[2,6],[1],[1,5],[1,2],[1],[2]]
        //输出
        //[null,-1,null,-1,null,null,2,-1]
        //预期结果
        //[null,-1,null,-1,null,null,2,6]
        Q146_LRUCache.LRUCache cache = new Q146_LRUCache.LRUCache(2);
        Assertions.assertEquals(-1, cache.get(2));
        cache.put(2, 6);
        Assertions.assertEquals(-1, cache.get(1));
        cache.put(1, 5);
        cache.put(1, 2);
        Assertions.assertEquals(2, cache.get(1));
        Assertions.assertEquals(6, cache.get(2));
    }

    @Test
    public void test4() {
        //输入:
        //["LRUCache","put","put","put","put","get","get"]
        //[[2],[2,1],[1,1],[2,3],[4,1],[1],[2]]
        //输出
        //[null,null,null,null,null,1,-1]
        //预期结果
        //[null,null,null,null,null,-1,3]
        Q146_LRUCache.LRUCache cache = new Q146_LRUCache.LRUCache(2);
        cache.put(2, 1);
        cache.put(1, 1);
        cache.put(2, 3);
        cache.put(4, 1);
        Assertions.assertEquals(1, cache.get(1));
        Assertions.assertEquals(3, cache.get(2));
    }
}
