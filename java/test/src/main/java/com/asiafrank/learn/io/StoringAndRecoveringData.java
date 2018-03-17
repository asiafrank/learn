package com.asiafrank.learn.io;

import java.io.*;

public class StoringAndRecoveringData {
    public static void main(String[] args) {
        String filename = "src/com/asiafrank/learn/io/Data.txt";
        try {
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(filename)));
            out.writeDouble(3.14159);
            out.writeUTF("That was pi");
            out.writeDouble(1.41413);
            out.writeUTF("Square root of 2");
            out.close();

            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(filename)));
            System.out.println(in.readDouble());
            // Only readUTF() will recover the
            // Java-UTF String properly
            System.out.println(in.readUTF());
            System.out.println(in.readDouble());
            System.out.println(in.readUTF());
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
