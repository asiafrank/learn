package com.asiafrank.scala.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ScalaTest
 * <p>
 * </p>
 * Created at 1/20/2017.
 *
 * @author zhangxf
 */
public final class ScalaTest {
    public void p() {
        System.out.println("ScalaTest invoked");
    }

    public static void main(String[] args) {
        String reg = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher("http://www.bilibili.com");

        System.out.println(m.matches());
    }

    public static byte valueOfByte() {
        return 'A';
    }
    public static byte[] valueOfBytes() {
        return new byte[]{'A', 'B'};
    }
    public static short valueOfShort() {
        return 1;
    }
    public static short[] valueOfShorts() {
        return new short[]{1, 2};
    }
    public static int valueOfInt() {
        return 1;
    }
    public static int[] valueOfInts() {
        return new int[]{1, 2};
    }
    public static long valueOfLong() {
        return 1L;
    }
    public static long[] valueOfLongs() {
        return new long[]{1L, 2L};
    }

    public static Byte valueOfByteObj() {
        return 'B';
    }
    public static Short valueOfShortObj() {
        return 2;
    }
    public static Integer valueOfIntObj() {
        return 2;
    }
    public static Long valueOfLongObj() {
        return 2L;
    }

    public static String valueOfString() {
        return "str";
    }
}
