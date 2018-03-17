package com.asiafrank.jni;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class TestJNIObjectArray {
    static {
        System.load(Libs.getLibPath("jni-hello-" + (Libs.isX64() ? "x64.dll" : "x86.dll")));
    }

    // Native method that receives an Integer[] and
    //  returns a Double[2] with [0] as sum and [1] as average
    private native Double[] sumAndAverage(Integer[] numbers);

    public static void main(String args[]) {
        Integer[] numbers = {11, 22, 32};  // auto-box
        Double[] results = new TestJNIObjectArray().sumAndAverage(numbers);
        System.out.println("In Java, the sum is " + results[0]);  // auto-unbox
        System.out.println("In Java, the average is " + results[1]);
    }
}
