package com.asiafrank.dp.observer.example5.event;

/**
 * Created by zhangxf on 11/21/2016.
 */
public class EventListenerImplB implements EventListener {
    private final int interested = EventType.B;

    @Override
    public void handle(int eventType) {
        if ((eventType & interested) != 0) {
            System.out.println("EventListenerImplB handle.");
        }
    }
}
