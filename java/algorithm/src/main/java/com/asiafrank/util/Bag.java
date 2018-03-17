package com.asiafrank.util;

/**
 * The <code>Bag</code> interface.
 *
 * @author Xiaofan Zhang
 */
public interface Bag<E> {

    /**
     * Add an element to the bag
     * @param e
     */
    void add(E e);

    /**
     * Tests if this queue is empty.
     *
     * @return <code>true</code> if and only if this queue contains
     *         no items; <code>false</code> otherwise.
     */
    boolean isEmpty();
}
