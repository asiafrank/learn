package com.asiafrank.dp.observer.example5.event;

import com.asiafrank.dp.observer.example5.ThreadPool;

/**
 * Created by zhangxf on 11/21/2016.
 */
public class EventListenerImplC implements EventListener {
    private final int interested = EventType.C;

    @Override
    public void handle(int eventType) {
        if ((eventType & interested) != 0) {
            System.out.println("EventListenerImplC handle.");
            // if there has a heavy operation use ThreadPool
            ThreadPool.execute(()->{
                System.out.println("Do much operations");
            });
        }
    }
}
