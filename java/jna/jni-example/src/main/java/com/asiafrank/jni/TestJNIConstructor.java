package com.asiafrank.jni;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class TestJNIConstructor {
    static {
        System.load(Libs.getLibPath("jni-hello-" + (Libs.isX64() ? "x64.dll" : "x86.dll")));
    }

    // Native method that calls back the constructor and return the constructed object.
    // Return an Integer object with the given int.
    private native Integer getIntegerObject(int number);

    public static void main(String args[]) {
        TestJNIConstructor obj = new TestJNIConstructor();
        System.out.println("In Java, the number is :" + obj.getIntegerObject(9999));
    }
}
