package com.java.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Example 4-3.
 */
public class ReverseTest {
    public static void main(String[] args) {
        try {
            InetAddress ia = InetAddress.getByName("114.55.66.65");
            System.out.println(ia.getCanonicalHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
