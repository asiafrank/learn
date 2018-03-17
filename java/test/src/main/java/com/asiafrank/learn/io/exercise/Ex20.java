package com.asiafrank.learn.io.exercise;

import com.asiafrank.learn.io.BinaryFile;
import com.asiafrank.learn.io.Directory;

import java.io.File;
import java.io.IOException;

public class Ex20 {
    public static void main(String[] args) {
        String[] arr = {"CA", "FE", "BA", "BE"};
        for(File file : Directory.walk(".", ".*\\.class").files) {
            try {
                byte[] ba = BinaryFile.read(file);
                boolean isMatch = false;
                for(int i = 0; i < 4; i++) {
                    isMatch = (Integer.toHexString(ba[i] & 0xff).toUpperCase().equals(arr[i]));
                    if (!isMatch) {
                        break;
                    }
                }
                if (isMatch) {
                    System.out.println(file.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
