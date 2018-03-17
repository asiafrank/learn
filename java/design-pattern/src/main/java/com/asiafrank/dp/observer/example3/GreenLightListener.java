package com.asiafrank.dp.observer.example3;

/**
 * 实现绿色交通灯的观察者类
 *
 * Created by zhangxf on 11/18/2016.
 */
public class GreenLightListener implements Listener {
    @Override
    public void updateSignal(String whichLight, int time) {
        if (whichLight.equals("green")) {
            System.out.println("绿灯亮了，请通过");
            System.out.println("持续时间: " + time);
        }
    }
}
