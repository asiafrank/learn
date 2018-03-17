package com.asiafrank.jni;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class TestJNIString {
    static {
        System.load(Libs.getLibPath("jni-hello-" + (Libs.isX64() ? "x64.dll" : "x86.dll")));
    }

    // Native method that receives a Java String and return a Java String
    private native String sayHello(String msg);

    public static void main(String args[]) {
        String result = new TestJNIString().sayHello("Hello from Java");
        System.out.println("In Java, the returned string is: " + result);
    }
}
