package com.asiafrank.util;

import com.asiafrank.annotation.ThreadSafe;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Iterator;

/**
 * The <code>FixedCapacityStack</code> class represents a last-in-first-out
 * (LIFO) stack of objects. When a stack is first created, it contains no items.
 *
 * If multiple threads access a fixed capacity stack concurrently, and at least
 * one of the threads modifies the list structurally, it <i>must</i> be
 * synchronized externally.
 *
 * @author Xiaofan Zhang
 */
@ThreadSafe
public class FixedCapacityStack<E> implements Stack<E>, Iterable<E> {

    /**
     * The top of the stack
     * When stack is created by constructor, top = bottom = null
     */
    private Node top;

    /**
     * The bottom of the stack, first item in the stack
     * When stack is created by constructor, top = bottom = null
     */
    private Node bottom;

    /**
     * The capacity of stack
     */
    private int capacity;

    /**
     * The number of items in the stack
     */
    private int size;

    /**
     * Object on which to synchronize
     * TODO: change to ReentrantLock, because in constructor the 'this' would escape.
     */
    private final Object mutex;

    /**
     * Init the stack
     *
     * @param cap capacity
     */
    private void init(int cap) {
        if (cap == 0) {
            throw new IllegalArgumentException("capacity must greater than 0");
        }
        top = bottom = null;
        this.capacity = cap;
        this.size = 0;
    }

    /**
     * Constructs a stack with capacity
     * Synchronize with <code>this</code>
     * @param cap capacity
     */
    public FixedCapacityStack(int cap) {
        init(cap);
        this.mutex = this;
    }

    /**
     * Constructs a stack with capacity and specified mutex for lock
     * @param cap capacity
     * @param mutex Object on which to synchronize
     */
    public FixedCapacityStack(int cap, Object mutex) {
        init(cap);
        this.mutex = mutex;
    }

    /**
     * Pushes an item onto the top of this stack.
     *
     * @param item the item to be pushed onto this stack.
     */
    @Override
    public void push(E item) {
        synchronized (mutex) {
            if (size >= capacity) {
                throw new IndexOutOfBoundsException("the stack is full");
            }
            top = new Node(top, item);
            if (size == 0) {
                bottom = top;
            }
            ++size;
        }
    }

    /**
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * @return The object at the top of this stack.
     *         if the stack is empty, return <code>null</code>.
     */
    @Override
    public E pop() {
        Node node;
        synchronized (mutex) {
            if (size == 0) {
                return null;
            }
            node = top;
            top = top.previous;
            node.previous = null;
            --size;
        }
        return node.val;
    }

    /**
     * Tests if this stack is empty.
     *
     * @return <code>true</code> if and only if this stack contains
     *         no items; <code>false</code> otherwise.
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    public boolean isFull() {
        return (size >= capacity);
    }

    /**
     * The number of items in the stack.
     *
     * @return The number of items in the stack.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in this stack in reverse sequence.
     *
     * <p>The returned iterator is fail-fast.
     *
     * @return an iterator over the elements in this stack in reverse sequence
     */
    @Override
    public Iterator<E> iterator() {
        return new ReverseIterator();
    }

    private class Node {
        private E val;
        private Node previous;

        public Node(Node previous, E val) {
            this.previous = previous;
            this.val = val;
        }
    }

    private class ReverseIterator implements Iterator<E> {
        private Node node = bottom;

        @Override
        public boolean hasNext() {
            return (node != null && node.previous != null);
        }

        @Override
        public E next() {
            Node n = node;
            if (node != null && node.previous != null) {
                node = node.previous;
            }
            return (n == null? null : n.val);
        }
    }

    /**
     * Test run
     * Input: to be or not to - be - - that - - - is
     */
    public static void main(String[] args) {
        FixedCapacityStack<String> s = new FixedCapacityStack<String>(100);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                s.push(item);
            else if (!s.isEmpty())
                StdOut.print(s.pop() + " ");
        }
        StdOut.println("(" + s.size() + " left on stack)");
    }
}
