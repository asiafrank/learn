package com.asiafrank.dp.observer.example5;

import com.asiafrank.dp.observer.example5.event.EventListener;

/**
 * Created by zhangxf on 11/18/2016.
 */
public interface Subject {
    void addListener(EventListener listener);
    void deleteListener(EventListener listener);
    void notifyListener();
}
