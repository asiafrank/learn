package com.asiafrank.util;

/**
 * The <code>Stack</code> interface represents a last-in-first-out
 * (LIFO) stack of objects.
 *
 * @author Xiaofan Zhang
 */
public interface Stack<E> {
    /**
     * Pushes an item onto the top of this stack.
     *
     * @param e the item to be pushed onto this stack.
     */
    void push(E e);

    /**
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * @return The object at the top of this stack.
     *         if the stack is empty, return <code>null</code>.
     */
    E pop();

    /**
     * Tests if this stack is empty.
     *
     * @return <code>true</code> if and only if this stack contains
     *         no items; <code>false</code> otherwise.
     */
    boolean isEmpty();

    /**
     * The number of items in the stack.
     *
     * @return The number of items in the stack.
     */
    int size();
}
