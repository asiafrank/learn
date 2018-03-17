package com.java.network;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Example 3-1. DigestThread
 */
public class DigestThread extends Thread {
    private String filename;

    public DigestThread(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while (din.read() != -1) ;
            din.close();
            byte[] digest = sha.digest();

            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        // assume args is the following array.
        if (args == null || args.length == 0) {
            args = new String[]{
                "/Users/asiafrank/workspace/personal/java/learn/src/com/asiafrank/learn/io/Alien.java",
                "/Users/asiafrank/workspace/personal/java/learn/src/com/asiafrank/learn/io/AvailableCharSets.java"
            };
        }

        for (String filename : args) {
            Thread t = new DigestThread(filename);
            t.start();
        }
    }
}
