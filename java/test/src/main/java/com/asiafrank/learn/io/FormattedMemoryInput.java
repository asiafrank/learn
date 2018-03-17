package com.asiafrank.learn.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Xiaofan Zhang on 4/5/2016.
 */
public class FormattedMemoryInput {
    public static void main(String[] args) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    BufferedInputFile.read("src/com/asiafrank/learn/io/FormattedMemoryInput.java").getBytes());
            DataInputStream in = new DataInputStream(byteArrayInputStream);
            while (true) {
                System.out.print((char)in.readByte());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("End of stream");
        }
    }
}
