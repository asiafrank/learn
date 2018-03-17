package com.asiafrank.se.proxy;

public class FooImpl implements Foo {
    @Override
    public Object bar(Object obj) {
        System.out.println("FooImpl bar() invoked");
        return null;
    }
}
