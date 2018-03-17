package com.asiafrank.dp.observer.example5;

import com.asiafrank.dp.observer.example5.event.EventListenerImplA;
import com.asiafrank.dp.observer.example5.event.EventListenerImplB;
import com.asiafrank.dp.observer.example5.event.EventListenerImplC;
import com.asiafrank.dp.observer.example5.event.EventType;

/**
 * Created by zhangxf on 11/21/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        EventListenerImplA eventA = new EventListenerImplA();
        EventListenerImplB eventB = new EventListenerImplB();
        EventListenerImplC eventC = new EventListenerImplC();

        SimpleSubject subject = new SimpleSubject();
        subject.addListener(eventA);
        subject.addListener(eventB);
        subject.addListener(eventC);

        ThreadPool.execute(subject::start);
        ThreadPool.execute(()->{
            subject.setEventType(EventType.A
                    | EventType.B
                    | EventType.C); // change eventType
        });
        Thread.sleep(1000); // do something else
        ThreadPool.shutdown();
    }
}
