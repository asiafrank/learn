package com.asiafrank.learn.clsload;

public class ClassLoaderDemo {
    public static void main(String[] args) {
        System.out.println("classloader0: " + ClassLoaderDemo.class.getClassLoader());
        System.out.println("classloader1: " + ClassLoaderDemo.class.getClassLoader().getParent());
        System.out.println("classloader2: " + ClassLoaderDemo.class.getClassLoader().getParent().getParent());
    }
}
