package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * https://leetcode-cn.com/problems/lru-cache/
 *
 * 问题146：LRU Cache
 * 运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制。
 * 它应该支持以下操作：获取数据 get 和 写入数据 put 。
 *
 * 获取数据 get(key) -
 *   如果关键字 (key) 存在于缓存中，
 *   则获取关键字的值（总是正数），
 *   否则返回 -1。
 *
 * 写入数据 put(key, value) -
 *   如果关键字已经存在，则变更其数据值；
 *   如果关键字不存在，则插入该组「关键字/值」。
 *   当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 *
 * 进阶:
 * 你是否可以在 O(1) 时间复杂度内完成这两种操作？
 *
 * 事例：
 * <pre>
 * LRUCache cache = new LRUCache(2); // 2 为缓存容量
 * cache.put(1,1);
 * cache.put(2,2);
 * cache.get(1);      // 返回  1
 * cache.put(3,3);    // 该操作会使得关键字 2 作废
 * cache.get(2);      // 返回 -1 (未找到)
 * cache.put(4,4);    // 该操作会使得关键字 1 作废
 * cache.get(1);      // 返回 -1 (未找到)
 * cache.get(3);      // 返回  3
 * cache.get(4);      // 返回  4
 * </pre>
 *
 * 思路：
 * 1.为了实现LRU（最近最少使用），需要维护一个双向链表，最近使用（get，put）都将这些元素放入链表尾部 O(1)。
 *   双向链表，是因为 get 时，将元素放入尾部时，还需要让 前继 连接到 后继，前继需要 prev 指针来获取，才能达到 O(1) 复杂度。
 * 2.为了使 get 方法 O(1)，使用 HashMap
 *
 * 运行见单元测试
 *
 * @author zhangxiaofan 2020/06/22-09:33
 */
public class Q146_LRUCache {
    static class LRUCache {
        private final int capacity;

        private final Node head;
        private final Node tail;

        private final Map<Integer, Node> map = new HashMap<>();

        static class Node {
            Node prev;
            Node next;

            int key;
            int value;

            public Node() {
            }

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        public LRUCache(int capacity) {
            this.capacity = capacity;
            // 头尾固定，避免判断 null 值
            this.head = new Node();
            this.tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            Node node = map.get(key);
            if (node == null) {
                return -1;
            }

            if (map.size() == 1) { // 只有一个元素 head,tail,node 都是它
                return node.value;
            }

            moveToEnd(node);
            return node.value;
        }

        public void put(int key, int value) {
            Node node = map.get(key);
            if (map.size() == capacity) {
                if (Objects.isNull(node)) { // put 的是 map 中不存在的，剔除最久未使用的
                    removeStart();
                } // else put 的是 map 中存在的，则无需剔除最久的
            }

            if (node == null) {
                Node n = new Node();
                n.key = key;
                n.value = value;
                appendToEnd(n);
                map.put(key, n);
            } else {
                node.value = value;
                moveToEnd(node);
            }
        }

        private void moveToEnd(Node node) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;

            appendToEnd(node);
        }

        // 将节点放入尾部
        private void appendToEnd(Node node) {
            Node prev = tail.prev;
            prev.next = node;
            node.prev = prev;

            node.next = tail;
            tail.prev = node;
        }

        // 移除第一个
        private void removeStart() {
            Node needToRemove = head.next;
            Node next = needToRemove.next;
            head.next = next;
            next.prev = head;

            map.remove(needToRemove.key);
            needToRemove.next = null;
            needToRemove.prev = null;
        }
    }
}
