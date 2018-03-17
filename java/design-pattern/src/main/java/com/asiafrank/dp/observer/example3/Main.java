package com.asiafrank.dp.observer.example3;

/**
 * 演示设计模式---观察者模式的demo
 */
public class Main {

    public static void main(String[] args) {
        // 创建主题，被监听对象，通常是一个自身状态属性可变的类
        Lights lights = new Lights();

        //创建三个观察者实例
        YellowLightListener yellow = new YellowLightListener();
        GreenLightListener green = new GreenLightListener();
        RedLightListener red = new RedLightListener();
        //向主题注册三个观察者，监听变化
        lights.addListener(green);
        lights.addListener(red);
        lights.addListener(yellow);
        //手动的改变交通灯的状态
        lights.setLight("red",30);
        lights.setLight("yellow", 10);
        lights.setLight("green", 20);
    }
}
