package com.asiafrank.dp.observer.example5.event;

/**
 * Created by zhangxf on 11/21/2016.
 */
public class EventListenerImplA implements EventListener {

    private final int interested = EventType.A;

    @Override
    public void handle(int eventType) {
        if ((eventType & interested) != 0) {
            System.out.println("EventListenerImplA handle.");
        }
    }
}
