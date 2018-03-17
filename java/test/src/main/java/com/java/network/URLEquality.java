package com.java.network;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Example 5-5. Are http://www.ibiblio.org and http://ibiblio.org the same
 */
public class URLEquality {
    public static void main(String[] args) {
        try {
            URL www = new URL("http://www.ibiblio.org/");
            URL ibiblio = new URL("http://ibiblio.org/");
            if (ibiblio.equals(www)) {
                System.out.println(ibiblio + " is the same as " + www);
            } else {
                System.out.println(ibiblio + " is not the same as " + www);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
