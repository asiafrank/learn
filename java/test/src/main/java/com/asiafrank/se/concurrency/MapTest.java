package com.asiafrank.se.concurrency;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Point> locations = new ConcurrentHashMap<>();
        locations.put("one", new Point(1, 0));
        locations.put("two", new Point(2, 0));
        locations.put("three", new Point(3, 0));

        Map<String, Point> unmodifiableMap = Collections.unmodifiableMap(locations);

        locations.replace("one", new Point(1, 3));
        locations.replace("two", new Point(2, 2));
        locations.replace("three", new Point(3, 4));

        System.out.println("---- locations ----");

        for (String key : locations.keySet()) {
            Point p = locations.get(key);
            System.out.println(key + ": x=" + p.x + " y=" + p.y);
        }

        System.out.println("---- unmodifiableMap ----");

        for (String key : unmodifiableMap.keySet()) {
            Point p = unmodifiableMap.get(key);
            System.out.println(key + ": x=" + p.x + " y=" + p.y);
        }
    }

    private static class Point {
        public final int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
