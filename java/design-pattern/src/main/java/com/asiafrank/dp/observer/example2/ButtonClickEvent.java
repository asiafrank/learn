package com.asiafrank.dp.observer.example2;

import java.util.EventObject;

/**
 * 事件类,包含了事件源
 */
public class ButtonClickEvent extends EventObject {

    /**
     * 字段：
     */
    private static final long serialVersionUID = 1L;
    // 事件源  
    private Object mSourceObject = null;
    private String mTag = "";

    /**
     * 构造函数
     */
    public ButtonClickEvent(Object sObject) {
        super(sObject);
        mSourceObject = sObject;
    }

    /**
     * 构造函数
     */
    public ButtonClickEvent(Object sObject, String tag) {
        super(sObject);
        mSourceObject = sObject;
        mTag = tag;
    }

    /**
     * 获取事件源
     * (non-Javadoc)
     *
     * @see java.util.EventObject#getSource()
     */
    public Object getSource() {
        return mSourceObject;
    }

    public void setSource(Object obj) {
        mSourceObject = obj;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }
}