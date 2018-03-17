package com.java.network;

import javax.xml.bind.DatatypeConverter;

/**
 * Example 3-8
 */
public class InstanceCallBackDigestUserInterface {
    private String filename;
    private byte[] digest;

    public InstanceCallBackDigestUserInterface(String filename) {
        this.filename = filename;
    }

    public void calculateDigest() {
        InstanceCallbackDigest cb = new InstanceCallbackDigest(filename, this);
        Thread t = new Thread(cb);
        t.start();
    }

    public void receiveDigest(byte[] digest) {
        this.digest = digest;
        System.out.println(this);
    }

    @Override
    public String toString() {
        String result = filename + ": ";
        if (digest != null) {
            result += DatatypeConverter.printHexBinary(digest);
        } else {
            result += "digest not avaliable";
        }
        return result;
    }

    public static void main(String[] args) {
        for (String filename : args) {
            // Calculate the digest
            InstanceCallBackDigestUserInterface d = new InstanceCallBackDigestUserInterface(filename);
            d.calculateDigest();
        }
    }
}
