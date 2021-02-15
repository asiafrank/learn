package com.asiafrank.labuladong.datastruct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 实现 LRU 算法(带上过期时间)
 * @author zhangxiaofan 2021/02/15-10:34
 */
public class LRUCacheWithTime {

    private int capacity;

    private final DoubleLink doubleLink = new DoubleLink();

    // key: key, value: Node
    private final Map<Integer, Node> map = new HashMap<>();

    public LRUCacheWithTime(int capacity) {
        this.capacity = capacity;
    }

    /**
     * map中如果存在，则替换 val，并且将 Node 换到 first 处
     * map中如果不存在，则在 first 处 插入 Node
     * @param key key
     * @param val value
     * @param expire 秒
     */
    public void put(int key, int val, int expire) {
        long now = System.currentTimeMillis();
        long endTime = now + expire * 1000L;
        if (map.containsKey(key)) {
            Node n = map.get(key);
            n.val = val;
            n.endTime = endTime;
            doubleLink.remove(n);
            doubleLink.addFirst(n);
        } else {
            Node n = new Node(key, val, endTime);

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
     *
     * 如果 Node 已过期，则删除，并且返回 -1
     * @param key key
     */
    public int get(int key) {
        Node n = map.get(key);
        if (Objects.isNull(n)) {
            return -1;
        }

        doubleLink.remove(n);

        long now = System.currentTimeMillis();
        if (n.endTime > now) // 表示已过期，删除之后，返回 null
            return -1;

        doubleLink.addFirst(n);
        return n.val;
    }

    private static class Node {
        Node prev, next;
        int key, val;
        // 过期时间维护
        long endTime;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }

        public Node(int key, int val, long endTime) {
            this.key = key;
            this.val = val;
            this.endTime = endTime;
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
}
