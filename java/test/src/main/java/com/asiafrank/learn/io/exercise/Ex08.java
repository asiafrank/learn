package com.asiafrank.learn.io.exercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Xiaofan Zhang on 28/4/2016.
 */
public class Ex08 {
    public static LinkedList<String> readLinesAsList(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String s;
        LinkedList<String> strings = new LinkedList<String>();
        while ((s = br.readLine()) != null) {
            strings.add(s);
        }
        br.close();
        return strings;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.next();
        // src/com/asiafrank/learn/io/BufferedInputFile.java
        LinkedList<String> ss = readLinesAsList(filename);
        Collections.reverse(ss);
        for (String s : ss) {
            System.out.println(s);
        }
    }
}
