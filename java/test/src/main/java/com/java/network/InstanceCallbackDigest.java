package com.java.network;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Example 3-7
 */
public class InstanceCallbackDigest implements Runnable {
    private String filename;
    private InstanceCallBackDigestUserInterface callback;

    public InstanceCallbackDigest(String filename, InstanceCallBackDigestUserInterface callback) {
        this.filename = filename;
        this.callback = callback;
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
            callback.receiveDigest(digest);
        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println(e);
        }
    }
}
