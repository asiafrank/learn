package com.asiafrank.learn.io.exercise;

import com.asiafrank.learn.io.BufferedInputFile;

import java.io.*;

public class Ex14 {
    private static String file = "src/com/asiafrank/learn/io/exercise/Ex14.out";

    public static void main(String[] args) {
        String type = "unbuffered";
        String filename = "src/com/asiafrank/learn/io/exercise/Ex14.java";
        long starTime = System.currentTimeMillis();

        if (type.equals("buffered")) {
            buffered(filename);
        } else {
            unbuffered(filename);
        }

        long endTime = System.currentTimeMillis();
        long spendTime = endTime - starTime;
        System.out.println("SpendTime: " + spendTime);
    }

    private static void unbuffered(String filename) {
        try {
            FileInputStream in = new FileInputStream(filename);
            PrintWriter out = new PrintWriter(file);
            int r;
            while ((r = in.read()) != -1) {
                out.print((char)r);
            }
            out.close();
            // show the stored file:
//            System.out.println(BufferedInputFile.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buffered(String filename) {
        try {
            BufferedReader in = new BufferedReader(
                    new StringReader(BufferedInputFile.read(filename)));
            PrintWriter out = new PrintWriter(file);
            String s;
            while ((s = in.readLine()) != null) {
                out.println(s);
            }
            out.close();
            // show the stored file:
//            System.out.println(BufferedInputFile.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
