package com.asiafrank.learn.io.exercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Ex12 {
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
        // src/com/asiafrank/learn/io/exercise/Ex12.java
        LinkedList<String> ss = readLinesAsList(filename);
        Collections.reverse(ss);

        PrintWriter out = new PrintWriter("src/com/asiafrank/learn/io/exercise/Ex12.out");
        int lineCount = 0;
        for (String s : ss) {
            System.out.println(s);
            out.println(++lineCount + ": " + s);
        }
        out.close();
    }
}
