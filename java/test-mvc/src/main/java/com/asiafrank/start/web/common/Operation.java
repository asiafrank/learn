package com.asiafrank.start.web.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Operation {
    private Object o;
    private Method m; // class method

    private Operation(Object o, Method m) {
        this.m = m;
        this.o = o;
    }

    public static Operation newInstance(Object o, Method m) {
        return new Operation(o, m);
    }

    public void invoke (Object... args) {
        try {
            m.invoke(o, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Method getM() {
        return m;
    }

    public void setM(Method m) {
        this.m = m;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
