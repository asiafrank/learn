package com.asiafrank.jni;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class TestJNIInstanceVariable {
    static {
        System.load(Libs.getLibPath("jni-hello-" + (Libs.isX64() ? "x64.dll" : "x86.dll")));
    }

    // Instance variables
    private int    number  = 88;
    private String message = "Hello from Java";

    // Declare a native method that modifies the instance variables
    private native void modifyInstanceVariable();

    // Test Driver
    public static void main(String args[]) {
        TestJNIInstanceVariable test = new TestJNIInstanceVariable();
        test.modifyInstanceVariable();
        System.out.println("In Java, int is " + test.number);
        System.out.println("In Java, String is " + test.message);
    }
}
