package com.asiafrank.se;

public class FinallyTest {
    public static void main(String[] args) {
        System.out.println("start");
        try {
            int a = 0;
            a++;
            System.out.println("a: " + a);
        } finally {
            System.out.println("finally");
        }

        try {
            System.out.println("b");
        } finally {
            System.out.println("b finally");
        }
    }
}
