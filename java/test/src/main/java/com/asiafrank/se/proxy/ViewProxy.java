package com.asiafrank.se.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ViewProxy implements InvocationHandler {
    private Map map;
    private Object obj;

    public static Object newInstance(Map map, Object obj, Class[] interfaces, ClassLoader cl) {
        return Proxy.newProxyInstance(cl, interfaces, new ViewProxy(map, obj));
    }

    public ViewProxy(Map map, Object obj) {
        this.map = map;
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Object rs = null;
        try {
            return m.invoke(obj, args);
        } catch (Exception e) {
            // ignore
            System.out.println("no method");
            String methodName = m.getName();
            if (methodName.startsWith("get")) {
                String name = methodName.substring(methodName.indexOf("get") + 3);
                return map.get(name);
            } else if (methodName.startsWith("set")) {
                String name = methodName.substring(methodName.indexOf("set") + 3);
                map.put(name, args[0]);
                return null;
            } else if (methodName.startsWith("is")) {
                String name = methodName.substring(methodName.indexOf("is") + 2);
                return (map.get(name));
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("Xiaofan");
        person.setAddress("China");
        person.setPhoneNumber("1886895523");
        HashMap map = new HashMap();
        IPerson ip = (IPerson) ViewProxy.newInstance(map, person, new Class[]{IPerson.class}, IPerson.class.getClassLoader());
        ip.setName("Frank");
        System.out.println("map: " + map.toString());
    }
}