package com.asiafrank.se.test;

public class Main {
    public void test(Object o) {
        System.out.println("Object");
    }

    public void test(String s) {
        System.out.println("String");
    }

    public static void main(String[] args) {
        Main that = new Main();
        that.test(null);
    }
}
