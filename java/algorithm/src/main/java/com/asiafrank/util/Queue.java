package com.asiafrank.util;

/**
 * The <code>Queue</code> interface represents a first-in-first-out
 * (FIFO) queue of objects.
 *
 * @author Xiaofan Zhang
 */
public interface Queue<E> {
    /**
     * Inserts the specified element into this queue.
     *
     * @param e the element to add
     */
    void enqueue(E e);

    /**
     * Retrieves and removes the head of this queue.
     *
     * @return the head of this queue
     */
    E dequeue();

    /**
     * Tests if this queue is empty.
     *
     * @return <code>true</code> if and only if this queue contains
     *         no items; <code>false</code> otherwise.
     */
    boolean isEmpty();

    /**
     * The number of items in the queue.
     *
     * @return The number of items in the queue.
     */
    int size();
}
