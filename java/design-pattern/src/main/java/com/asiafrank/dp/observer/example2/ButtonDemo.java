package com.asiafrank.dp.observer.example2;

import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 事件源模拟类, 采用的是观察者模式
 */
public class ButtonDemo {

    public static void main(String[] args) {
        ButtonDemo buttonDemo = new ButtonDemo("Hello, I am a ButtonDemo");
        // 添加监听器
        buttonDemo.AddItemClickListener(new ButtonClickListener());
        // 事件触发
        buttonDemo.ButtonClick();
    }

    // item文本文字  
    private String mItemName = "";
    // 监听器哈希集合,可以注册多个监听器  
    private Set<EventListener> mClickListeners = null;

    /**
     * 构造函数
     */
    public ButtonDemo() {
        //  监听器列表  
        mClickListeners = new HashSet<EventListener>();
        mItemName = "Defualt Item Name";
    }

    /**
     * 构造函数
     */
    public ButtonDemo(String itemString) {
        mItemName = itemString;
        mClickListeners = new HashSet<EventListener>();
    }

    /**
     * 添加监听器
     */
    public void AddItemClickListener(EventListener listener) {
        // 添加到监听器列表  
        this.mClickListeners.add(listener);
    }

    /**
     * 模拟点击事件,触发事件则通知所有监听器
     */
    public void ButtonClick() {
        // 通知所有监听者
        Notifies();
    }

    /**
     * 通知所有监听者
     */
    private void Notifies() {
        Iterator<EventListener> iterator = mClickListeners.iterator();
        while (iterator.hasNext()) {
            // 获取当前的对象  
            ButtonClickListener listener = (ButtonClickListener) iterator.next();
            // 事件触发,事件的构造函数参数为事件源  
            listener.ButtonClicked(new ButtonClickEvent(this));
        }
    }

    /**
     * 返回该项的名字
     */
    public String getItemString() {
        return mItemName;
    }
}