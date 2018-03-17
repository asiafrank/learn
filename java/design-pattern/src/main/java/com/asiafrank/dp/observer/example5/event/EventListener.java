package com.asiafrank.dp.observer.example5.event;

/**
 * An interface to be implemented by everyone interested in some events
 */
public interface EventListener extends java.util.EventListener {
    void handle(int eventType);
}