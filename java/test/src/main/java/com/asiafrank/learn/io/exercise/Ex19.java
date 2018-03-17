package com.asiafrank.learn.io.exercise;

import com.asiafrank.learn.io.BinaryFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Ex19 {
    public static void main(String[] args) {
        String fileName = "/Users/asiafrank/Desktop/TranscriptFeynman.pdf";
        Map<Byte, Integer> map = new HashMap<Byte, Integer>();
        try {
            byte[] data = BinaryFile.read(fileName);
            for (byte b : data) {
                if (map.containsKey(b)) {
                    map.put(b, map.get(b) + 1);
                } else {
                    map.put(b, 1);
                }
            }

            // TODO: sort the map
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
