package com.asiafrank.learn.io.exercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Xiaofan Zhang on 28/4/2016.
 */
public class Ex07 {
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

    public static void main(String[] args) throws IOException {
        LinkedList<String> ss = readLinesAsList("src/com/asiafrank/learn/io/BufferedInputFile.java");
        Collections.reverse(ss);
        for (String s : ss) {
            System.out.println(s);
        }
    }
}
