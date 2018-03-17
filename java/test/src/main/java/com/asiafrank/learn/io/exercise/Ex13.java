package com.asiafrank.learn.io.exercise;

import com.asiafrank.learn.io.BufferedInputFile;

import java.io.*;

public class Ex13 {
    private static String file = "src/com/asiafrank/learn/io/exercise/Ex13.out";

    public static void main(String[] args) {
        try {
            LineNumberReader in = new LineNumberReader(new FileReader("src/com/asiafrank/learn/io/exercise/Ex13.java"));
            PrintWriter out = new PrintWriter(file);
            String s;
            while ((s = in.readLine()) != null) {
                out.println(in.getLineNumber() + ": " + s);
            }
            out.close();
            // show the stored file:
            System.out.println(BufferedInputFile.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
