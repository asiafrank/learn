package com.asiafrank.util;

import com.asiafrank.annotation.ThreadSafe;

import java.util.Iterator;

/**
 * The <code>Bag</code>.
 *
 * @author Xiaofan Zhang
 */
@ThreadSafe
public class LinkedBag<E> implements Bag<E>, Iterable<E> {

    /**
     * The first element of the bag
     * When Bag is created by constructor, first = last = null
     */
    private Node first;

    /**
     * The last element of the bag
     * When bag is created by constructor, first = last = null
     */
    private Node last;

    /**
     * The number of items in the bag
     */
    private int size;

    /**
     * Object on which to synchronize
     *
     * TODO: change to ReentrantLock, because in constructor the 'this' would escape.
     */
    private final Object mutex;

    /**
     * Init the bag
     */
    private void init() {
        first = last = null;
        size = 0;
    }

    /**
     * Constructs a bag
     * Synchronize with <code>this</code>
     */
    public LinkedBag() {
        init();
        this.mutex = this;
    }

    /**
     * Constructs a bag with specified mutex for lock
     *
     * @param mutex Object on which to synchronize
     */
    public LinkedBag(Object mutex) {
        init();
        this.mutex = mutex;
    }

    /**
     * Add an element to the bag
     * @param e
     */
    @Override
    public void add(E e) {
        Node n = new Node(e, null);
        synchronized (mutex) {
            if (isEmpty()) {
                first = last = n;
            }
            last.next = n;
            last = n;
            ++size;
        }
    }

    /**
     * Tests if this queue is empty.
     *
     * @return <code>true</code> if and only if this queue contains
     *         no items; <code>false</code> otherwise.
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Node {
        private E val;
        private Node next;

        public Node(E val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    private class Itr implements Iterator<E> {
        private Node pos = first; // position

        @Override
        public boolean hasNext() {
            return (pos != null && pos.next != null);
        }

        @Override
        public E next() {
            Node n = pos;
            if (pos != null && pos.next != null) {
                pos = pos.next;
            }
            return n.val;
        }
    }
}
