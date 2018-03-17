package com.asiafrank.learn.io.exercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Xiaofan Zhang on 28/4/2016.
 */
public class Ex10 {
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
        if (args.length <= 0) {
            System.out.println("no args!");
            return;
        }

        if (args.length < 1) {
            System.out.println("no words!");
            return;
        }

        StringBuilder patternStr = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            patternStr.append("(.+)?").append(args[i]).append("(.+)?").append("|");
        }
        String filename = args[0];
        Pattern p = Pattern.compile(patternStr.toString());
        // src/com/asiafrank/learn/io/BufferedInputFile.java
        LinkedList<String> ss = readLinesAsList(filename);
        Collections.reverse(ss);
        for (String s : ss) {
            Matcher m = p.matcher(s);
            if (m.matches())
                System.out.println(s);
        }
    }
}
