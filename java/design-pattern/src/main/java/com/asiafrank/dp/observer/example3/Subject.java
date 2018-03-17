package com.asiafrank.dp.observer.example3;

/**
 * Created by zhangxf on 11/18/2016.
 */
public interface Subject {
    void addListener(Listener listener);
    void deleteListener(Listener listener);
    void notifyListener();
}
