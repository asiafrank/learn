package com.asiafrank.jni;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class TestJNIPrimitiveArray {
    static {
        System.load(Libs.getLibPath("jni-hello-" + (Libs.isX64() ? "x64.dll" : "x86.dll")));
    }

    // Declare a native method sumAndAverage() that receives an int[] and
    //  return a double[2] array with [0] as sum and [1] as average
    private native double[] sumAndAverage(int[] numbers);

    // Test Driver
    public static void main(String args[]) {
        int[] numbers = {22, 33, 33};
        double[] results = new TestJNIPrimitiveArray().sumAndAverage(numbers);
        System.out.println("In Java, the sum is " + results[0]);
        System.out.println("In Java, the average is " + results[1]);
    }
}
