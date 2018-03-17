package com.asiafrank.learn.io.exercise;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Xiaofan Zhang on 28/4/2016.
 */
public class Ex09 {
    private static LinkedList<String> readLinesAsList(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String s;
        LinkedList<String> strings = new LinkedList<String>();
        while ((s = br.readLine()) != null) {
            strings.add(s);
        }
        br.close();
        return strings;
    }

    private static void writeLinesToUpperCase(LinkedList<String> ss) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (String s : ss)
            bw.write(s.toUpperCase() + "\n");
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String filename = sc.next();
        // src/com/asiafrank/learn/io/BufferedInputFile.java
        LinkedList<String> ss = readLinesAsList(filename);
        Collections.reverse(ss);
        writeLinesToUpperCase(ss);
    }
}
