package com.asiafrank.labuladong.datastruct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 实现 LRU 算法
 * @author zhangxiaofan 2021/02/15-10:34
 */
public class LRUCache {

    private int capacity;

    private final DoubleLink doubleLink = new DoubleLink();

    // key: key, value: Node
    private final Map<Integer, Node> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    /**
     * map中如果存在，则替换 val，并且将 Node 换到 first 处
     * map中如果不存在，则在 first 处 插入 Node
     * @param key key
     * @param val value
     */
    public void put(int key, int val) {
        if (map.containsKey(key)) {
            Node n = map.get(key);
            n.val = val;
            doubleLink.remove(n);
            doubleLink.addFirst(n);
        } else {
            Node n = new Node(key, val);

            if (doubleLink.getSize() == capacity) { // 满了，淘汰旧的
                Node old = doubleLink.removeLast();
                map.remove(old.key);
            }

            doubleLink.addFirst(n);
            map.put(key, n);
        }
    }

    /**
     * map 中如果存在，则将 Node 放入 first
     * map 中如果不存在，则返回 -1
     * @param key key
     */
    public int get(int key) {
        Node n = map.get(key);
        if (Objects.isNull(n)) {
            return -1;
        }

        doubleLink.remove(n);
        doubleLink.addFirst(n);
        return n.val;
    }

    private static class Node {
        Node prev, next;
        int key, val;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * 双向链表
     */
    private static class DoubleLink {
        int size;
        Node head;
        Node tail;

        public DoubleLink() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        void addFirst(Node n) {
            Node next = head.next;
            head.next = n;
            next.prev = n;

            n.prev = head;
            n.next = next;

            size++;
        }

        void remove(Node n) {
            Node prev = n.prev;
            Node next = n.next;
            prev.next = next;
            next.prev = prev;

            size--;
        }

        // 删除链表中最后⼀个节点，并返回该节点，时间 O(1)
        Node removeLast() {
            Node last = tail.prev;
            if (last == head) {
                throw new IllegalArgumentException("no element to remove");
            }

            remove(last);
            return last;
        }

        public int getSize() {
            return size;
        }
    }

    public static void main(String[] args) {
        /* 缓存容量为 2 */

        LRUCache cache = new LRUCache(2);

        // 你可以把 cache 理解成⼀个队列
        // 假设左边是队头，右边是队尾
        // 最近使⽤的排在队头，久未使⽤的排在队尾
        // 圆括号表⽰键值对 (key, val)

        cache.put(1, 1);
        // cache = [(1, 1)]

        cache.put(2, 2);
        // cache = [(2, 2), (1, 1)]

        cache.get(1);
        // 返回 1
        // cache = [(1, 1), (2, 2)]
        // 解释：因为最近访问了键 1，所以提前⾄队头
        // 返回键 1 对应的值 1

        cache.put(3, 3);
        // cache = [(3, 3), (1, 1)]
        // 解释：缓存容量已满，需要删除内容空出位置
        // 优先删除久未使⽤的数据，也就是队尾的数据
        // 然后把新的数据插⼊队头

        cache.get(2);
        // 返回 -1 (未找到)
        // cache = [(3, 3), (1, 1)]
        // 解释：cache 中不存在键为 2 的数据

        cache.put(1, 4);
        // cache = [(1, 4), (3, 3)]
    }
}
