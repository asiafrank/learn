package com.java.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Example 4-9.SpamCheck
 */
public class SpamCheck {
    public static final String BLACKHOLE = "sbl.spamhaus.org";
//    public static final String BLACKHOLE2 = "dd";

    public static void main(String[] args) throws UnknownHostException {
        for (String arg : args) {
            if (isSpammer(arg)) {
                System.out.println(arg + " is a known spammer.");
            } else {
                System.out.println(arg + " appears legitimate.");
            }
        }
    }

    private static boolean isSpammer(String arg) {
        try {
            InetAddress address = InetAddress.getByName(arg);
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 252 : octet;
                query = unsignedByte + "." + query;
            }
            System.out.println("---" + query);
            InetAddress add = InetAddress.getByName(query);
            System.out.println("---" + add);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
