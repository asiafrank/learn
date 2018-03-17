package com.java.network;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Example 4-10.Process web server logfiles
 */
public class Weblog {
    public static void main(String[] args) {
        try (FileInputStream fin = new FileInputStream(args[0]);
             Reader in = new InputStreamReader(fin);
             BufferedReader bin = new BufferedReader(in);)
        {
            String entry = bin.readLine();
            for (;entry != null; entry = bin.readLine()) {
                // separate out the IP address
                int index = entry.indexOf(' ');
                String ip = entry.substring(0, index);
                String theRest = entry.substring(index);

                // Ask DNS for the hostname and print it out
                try {
                    InetAddress address = InetAddress.getByName(ip);
                    System.out.println(address.getHostName() + theRest);
                } catch (UnknownHostException e) {
                    System.out.println(entry);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }
}
