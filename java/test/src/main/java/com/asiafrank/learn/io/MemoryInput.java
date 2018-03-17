package com.asiafrank.learn.io;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Xiaofan Zhang on 4/5/2016.
 */
public class MemoryInput {
    public static void main(String[] args) {
        try {
            StringReader in = new StringReader(BufferedInputFile.read("src/com/asiafrank/learn/io/MemoryInput.java"));
            int c;
            while ((c = in.read()) != -1) {
                System.out.print((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
