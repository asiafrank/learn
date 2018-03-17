package com.asiafrank.dp.observer.example3;

/**
 * Created by zhangxf on 11/18/2016.
 */
public class YellowLightListener implements Listener {
    @Override
    public void updateSignal(String whichLight, int time) {
        if (whichLight.equals("yellow")) {
            System.out.println("黄灯亮了，请稍等");
            System.out.println("持续时间： " + time);
        }
    }
}
