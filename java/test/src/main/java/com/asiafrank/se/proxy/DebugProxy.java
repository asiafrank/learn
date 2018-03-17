package com.asiafrank.se.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DebugProxy implements InvocationHandler {
    private Object obj;

    public static Object newInstance(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
            obj.getClass().getInterfaces(),
            new DebugProxy(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            System.out.println("before method " + method.getName());
            result = method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected illegal access exception" + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("unexpected illegal argument exception" + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("unexpected invocation target exception" + e.getMessage());
        } finally {
            System.out.println("after method " + method.getName());
        }
        return result;
    }

    public DebugProxy(Object obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {
        FooImpl fi = new FooImpl();
        Foo f = (Foo)DebugProxy.newInstance(fi);
        f.bar(null);
    }
}
