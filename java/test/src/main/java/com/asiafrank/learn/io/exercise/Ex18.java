package com.asiafrank.learn.io.exercise;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Ex18 extends ArrayList<String> {
    // Read a file as a single string:
    public static String read(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
        try {
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
        } finally {
            in.close();
        }
        return sb.toString();
    }

    // Write a single file in one method call:
    public static void write(String fileName, String text) throws IOException {
        PrintWriter out = null;
        out = new PrintWriter(new File(fileName).getAbsoluteFile());
        try {
            out.print(text);
        } finally {
            out.close();
        }
    }

    // Read a file, splite by any regular expression:
    public Ex18(String fileName, String splitter) throws IOException {
        super(Arrays.asList(read(fileName).split(splitter)));
        // Regular expression split() often leaves an empty
        // String at the first position:
        if (get(0).equals("")) {
            remove(0);
        }
    }

    // Normally read by lines:
    public Ex18(String fileName) throws IOException {
        this(fileName, "\n");
    }

    public void write(String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
        try {
            this.forEach(out::println);
        } finally {
            out.close();
        }
    }

    // Simple test:
    public static void main(String[] args) {
        try {
            String prefix = "src/com/asiafrank/learn/io/";

            String file = read(prefix + "TextFile.java");
            write(prefix + "text.txt", file);

            Ex18 text = new Ex18(prefix + "text.txt");
            text.write(prefix + "text2.txt");

            // Break into unique sorted list of words:
            TreeSet<String> words = new TreeSet<String>(new Ex18(prefix + "TextFile.java", "\\W+"));

            //Display the capitalized words:
            System.out.println(words.headSet("a"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
