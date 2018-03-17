package com.asiafrank.jni;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class HelloJNI {
    static {
        System.load(Libs.getLibPath("jni-hello-" + (Libs.isX64() ? "x64.dll" : "x86.dll")));
    }

    // Declare a native method sayHello() that receives nothing and returns void
    public native void sayHello();

    // Test Driver
    public static void main(String[] args) {
        new HelloJNI().sayHello(); // invoke the native method
    }
}
