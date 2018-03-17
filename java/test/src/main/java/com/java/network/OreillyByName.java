package com.java.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Example 4-1.
 */
public class OreillyByName {
    public static void main(String[] args) {
        try {
            InetAddress[] addresses = InetAddress.getAllByName("www.oreilly.com");
            for (InetAddress address : addresses) {
                System.out.println(address);
            }
        } catch (UnknownHostException e) {
            System.out.println("Could not find www.oreilly.com");
        }
    }
}
