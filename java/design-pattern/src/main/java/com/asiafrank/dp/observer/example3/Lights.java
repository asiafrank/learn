package com.asiafrank.dp.observer.example3;

import java.util.ArrayList;

/**
 * Created by zhangxf on 11/18/2016.
 */
public class Lights implements Subject {

    private ArrayList<Listener> lights;
    private String whichLight;
    private String currentLight = "green";
    private int time;

    public Lights() {
        this.lights = new ArrayList();
    }

    /**
     * 实现 添加一个观察者操作
     * @param listener
     */
    @Override
    public void addListener(Listener listener) {
        lights.add(listener);
    }

    /**
     * 实现 删除一个观察者操作
     * @param listener
     */
    @Override
    public void deleteListener(Listener listener) {
        int index = lights.indexOf(listener);
        if (index != -1) {
            lights.remove(index);
        }
    }

    /**
     * 实现通知的机制，通知每一个观察者
     */
    @Override
    public void notifyListener() {
        int size = lights.size();
        for (int i = 0; i < size; i++) {
            Listener listener = lights.get(i);
            listener.updateSignal(whichLight, time);
        }
    }

    /**
     * 更新主题状态数据的方法
     */
    public void setLight(String whichLight, int time) {
        this.whichLight = whichLight;
        this.time = time;
        // 检查状态是否发生了变化
        check();
        this.currentLight = whichLight;
    }

    /**
     * 实现检查状态的函数
     */
    private void check() {
        if (!this.currentLight.equals(this.whichLight)) {
            notifyListener();
        }
    }
}
