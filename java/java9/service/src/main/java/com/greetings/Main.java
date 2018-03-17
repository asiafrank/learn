package com.greetings;

import org.astro.World;

/**
 * http://openjdk.java.net/projects/jigsaw/quick-start
 * @author zhangxf created at 12/22/2017.
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Greetings! %s!%n", World.name());
    }
}