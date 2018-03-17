package com.java.network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Example 5-3. Download an object
 */
public class ContentGetter {
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                URL u = new URL(args[0]);
                Object o = u.getContent();
                System.out.println("I got a " + o.getClass().getName());
            } catch (MalformedURLException e) {
                System.err.println(args[0] + " is not a parseable URL");
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
