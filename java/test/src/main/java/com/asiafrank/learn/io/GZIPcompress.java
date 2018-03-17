package com.asiafrank.learn.io;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * The compress only support [A-z] file or binary file
 * do not support Chinese or other language
 */
public class GZIPcompress {
    public static void main(String[] args) throws IOException {
        String fileName = "src/com/asiafrank/learn/io/text.txt";
        String zipName = "src/com/asiafrank/learn/io/test.gz";

        BufferedReader in = new BufferedReader(new FileReader(fileName));
        BufferedOutputStream out = new BufferedOutputStream(
                new GZIPOutputStream(new FileOutputStream(zipName)));
        System.out.println("Writing file");
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        in.close();
        out.close();

        System.out.println("Reading file");
        BufferedReader in2 = new BufferedReader(new InputStreamReader(
                new GZIPInputStream(new FileInputStream(zipName))));
        String s;
        while ((s = in2.readLine()) != null) {
            System.out.println(s);
        }
    }
}
