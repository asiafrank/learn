package com.asiafrank.jni;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class TestJNIPrimitive {
    static {
        System.load(Libs.getLibPath("jni-hello-" + (Libs.isX64() ? "x64.dll" : "x86.dll")));
    }

    // Declare a native method average() that receives two ints and return a double containing the average
    private native double average(int n1, int n2);

    // Test Driver
    public static void main(String args[]) {
        System.out.println("In Java, the average is " + new TestJNIPrimitive().average(3, 2));
    }
}
