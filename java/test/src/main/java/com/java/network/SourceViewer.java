package com.java.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Example 5-2. Download a web page
 */
public class SourceViewer {
    public static void main(String[] args) {
        if (args.length > 0) {
            InputStream in = null;
            try {
                URL u = new URL(args[0]);
                in = u.openStream();
                // buffer the input to increase performance
                Reader r = new InputStreamReader(in);
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
            } catch (MalformedURLException ex) {
                System.err.println(args[0] + " is not aparseable URL");
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        }
    }
}
