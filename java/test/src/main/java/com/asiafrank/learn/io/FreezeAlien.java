package com.asiafrank.learn.io;

import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class FreezeAlien {
    public static void main(String[] args) throws Exception {
        String fileName = "src/com/asiafrank/learn/io/X.file";
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
        Alien quellek = new Alien();
        out.writeObject(quellek);
    }
}
