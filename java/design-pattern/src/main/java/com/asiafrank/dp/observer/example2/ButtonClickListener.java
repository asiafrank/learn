package com.asiafrank.dp.observer.example2;

import java.util.EventListener;

/**
 * 监听器
 */
public class ButtonClickListener implements EventListener {
    public void ButtonClicked(ButtonClickEvent event ) {
        // 获取事件源  
        ButtonDemo source = (ButtonDemo)event.getSource();  
        System.out.println("内部静态监听类@_@ 你点击的是 : " + source.getItemString()) ;  
    }
}