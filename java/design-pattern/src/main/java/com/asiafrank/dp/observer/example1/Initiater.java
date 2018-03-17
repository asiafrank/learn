package com.asiafrank.dp.observer.example1;

import java.util.ArrayList;
import java.util.List;

public class Initiater {
    private List<EventListener> listeners = new ArrayList<>();

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void notifyAllListeners() {
        System.out.println("Notify Listeners");

        // Notify everybody that may be interested.
        for (EventListener listener : listeners) {
            listener.handle();
        }
    }
}