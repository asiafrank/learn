package com.asiafrank.dp.observer.example5;

import com.asiafrank.dp.observer.example5.event.EventListener;
import com.asiafrank.dp.observer.example5.event.EventType;

import java.util.ArrayList;

/**
 * Created by zhangxf on 11/21/2016.
 */
public class SimpleSubject implements Subject {
    private final ArrayList<EventListener> listeners = new ArrayList<>();

    private int eventType = EventType.A;

    public void start() {
        System.out.println("---- start ----");
        notifyListener();
        System.out.println("---- end ----");
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
        notifyListener();
    }

    @Override
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void deleteListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListener() {
        for (EventListener el : listeners) {
            el.handle(eventType);
        }
    }
}
