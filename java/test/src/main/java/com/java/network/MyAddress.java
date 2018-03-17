package com.java.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Example 4-2.
 */
public class MyAddress {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
        } catch (UnknownHostException e) {
            System.out.println("Could not find this computer's address.");
        }
    }
}

