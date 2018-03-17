package com.asiafrank.learn.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UsingRandomAccessFile {
    private static String file = "src/com/asiafrank/learn/io/rtest.txt";

    private static void display() {
        try {
            RandomAccessFile rf = new RandomAccessFile(file, "r");
            for (int i = 0; i < 7; i++) {
                System.out.println("Value " + i + ": " + rf.readDouble());
            }
            System.out.println(rf.readUTF());
            rf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            RandomAccessFile rf = new RandomAccessFile(file, "rw");
            for (int i = 0; i < 7; i++) {
                rf.writeDouble(i * 1.414);
            }
            rf.writeUTF("The end of the file");
            rf.close();
            display();
            rf = new RandomAccessFile(file, "rw");
            rf.seek(5 * 8);
            rf.writeDouble(47.001);
            rf.close();
            display();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
