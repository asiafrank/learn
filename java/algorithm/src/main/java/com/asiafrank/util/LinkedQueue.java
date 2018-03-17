package com.asiafrank.util;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * The <code>Queue</code> interface represents a first-in-first-out
 * (FIFO) queue of objects. When a queue is first created, it contains no items.
 *
 * If multiple threads access a queue concurrently, and at least
 * one of the threads modifies the list structurally, it <i>must</i> be
 * synchronized externally.
 *
 * @author Xiaofan Zhang
 */
public class LinkedQueue<E> implements Queue<E>, Iterable<E> {

    /**
     * The first element of the queue
     * When queue is created by constructor, first = last = null
     */
    private Node first;

    /**
     * The last element of the queue
     * When queue is created by constructor, first = last = null
     */
    private Node last;

    /**
     * The number of items in the queue
     */
    private int size;

    /**
     * Object on which to synchronize
     * TODO: change to ReentrantLock, because in constructor the 'this' would escape.
     */
    private final Object mutex;

    /**
     * Init the queue
     */
    private void init() {
        this.first = this.last = null;
        this.size = 0;
    }

    /**
     * Constructs a queue
     * Synchronize with <code>this</code>
     */
    public LinkedQueue() {
        init();
        this.mutex = this;
    }

    /**
     * Constructs a queue with specified mutex for lock
     *
     * @param mutex Object on which to synchronize
     */
    public LinkedQueue(Object mutex) {
        init();
        this.mutex = (mutex == null) ? this : mutex;
    }

    /**
     * Inserts the specified element into this queue.
     *
     * @param e the element to add
     */
    @Override
    public void enqueue(E e) {
        Node node = new Node(e, null);
        synchronized (mutex) {
            if (size == 0) {
                first = last = node;
            } else {
                last.next = node;
                last = node;
            }
            ++size;
        }
    }

    /**
     * Retrieves and removes the head of this queue.
     *
     * @return the head of this queue
     */
    @Override
    public E dequeue() {
        if (size == 0) {
            return null;
        }

        synchronized (mutex) {
            if (size == 1) { // first == last
                Node node = first;
                first = null;
                last = null;
                --size;
                return node.val;
            }

            // size > 1
            Node node = first;
            first = first.next;
            --size;
            return node.val;
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

    /**
     * The number of items in the queue.
     *
     * @return The number of items in the queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in this queue in reverse sequence.
     *
     * <p>The returned iterator is fail-fast.
     *
     * @return an iterator over the elements in this queue in reverse sequence
     */
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
        private Node pos = first;

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

    /**
     * Test run
     * Input: to be or not to - be - - that - - - is
     */
    public static void main(String[] args) { // Create a queue and enqueue/dequeue strings.
        Queue<String> q = new LinkedQueue<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                q.enqueue(item);
            else if (!q.isEmpty())
                StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}
