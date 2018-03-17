package com.asiafrank.se;

public class VMOptionsTest {
    public static void main(String[] args) {
        // VM Option: -Dtest=true
        String str = System.getProperty("test");
        System.out.println("str: " + str);
    }
}
