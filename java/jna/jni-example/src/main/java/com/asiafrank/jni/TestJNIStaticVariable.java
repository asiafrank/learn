package com.asiafrank.jni;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class TestJNIStaticVariable {
    static {
        System.load(Libs.getLibPath("jni-hello-" + (Libs.isX64() ? "x64.dll" : "x86.dll")));
    }

    // Static variables
    private static double number = 55.66;

    // Declare a native method that modifies the static variable
    private native void modifyStaticVariable();

    // Test Driver
    public static void main(String args[]) {
        TestJNIStaticVariable test = new TestJNIStaticVariable();
        test.modifyStaticVariable();
        System.out.println("In Java, the double is " + number);
    }
}
