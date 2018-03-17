package com.asiafrank.learn.io;

import java.io.*;

/**
 * Created by Xiaofan Zhang on 4/5/2016.
 */
public class TestEOF {
    public static void main(String[] args) {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(
                    new FileInputStream("src/com/asiafrank/learn/io/TestEOF.java")));
            while (in.available() != 0) {
                System.out.print((char)in.readByte());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
