package com.asiafrank.learn.io.xfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ThawAlien {
    public static void main(String[] args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(new File("src/com/asiafrank/learn/io", "X.file")));
        Object mystery = in.readObject();
        System.out.println(mystery.getClass());
    }
}
