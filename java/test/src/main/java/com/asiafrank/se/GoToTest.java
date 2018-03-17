package com.asiafrank.se;

public class GoToTest {
    public static void main(String[] args) throws Exception {
        int a = 0;
        retry:
        for (;;) {
            Thread.sleep(1000);
            System.out.println("" + a);
            for (;;) {
                if (a == 1) {
                    break retry;
                } else if (a == 2) {
                    continue retry;
                }
                ++a;
            }
        }

        System.out.println("finish");
    }
}
