package com.asiafrank.learn.io.exercise;

import com.asiafrank.learn.io.TextFile;

import java.util.HashMap;
import java.util.Map;

public class Ex17 {
    public static void main(String[] args) {
        String prefix = "src/com/asiafrank/learn/io/";
        TextFile text = new TextFile(prefix + "TextFile.java", "");
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (String s : text) {
            Character c = s.charAt(0);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        System.out.println(map);
    }
}
