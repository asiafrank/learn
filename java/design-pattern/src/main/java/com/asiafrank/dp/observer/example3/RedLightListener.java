package com.asiafrank.dp.observer.example3;

/**
 * 实现红色交通灯的观察者类，相当于ConcretObserver类
 * <p>
 * Created by zhangxf on 11/18/2016.
 */
public class RedLightListener implements Listener {
    @Override
    public void updateSignal(String whichLight, int time) {
        if (whichLight.equals("red")) {
            System.out.println("红灯亮了，禁止通行");
            System.out.println("持续时间： " + time);
        }
    }
}
