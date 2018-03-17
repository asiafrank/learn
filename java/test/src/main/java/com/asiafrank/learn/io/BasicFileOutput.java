package com.asiafrank.learn.io;

import net.mindview.util.Print;

import java.io.*;

/**
 * Created by Xiaofan Zhang on 7/5/2016.
 */
public class BasicFileOutput {
    private static String file = "src/com/asiafrank/learn/io/BasicFileOutput.out";

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(
                    new StringReader(BufferedInputFile.
                            read("src/com/asiafrank/learn/io/BasicFileOutput.java")));
//            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            PrintWriter out = new PrintWriter(file);
            int lineCount = 1;
            String s;
            while ((s = in.readLine()) != null) {
                out.println(lineCount++ + ": " + s);
            }
            out.close();
            // show the stored file:
            System.out.println(BufferedInputFile.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
