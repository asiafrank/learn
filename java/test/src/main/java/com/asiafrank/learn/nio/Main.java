package com.asiafrank.learn.nio;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int run = 0;
        if (run == 0) {
            runReactorSingleThread();
        } else {
            runReactorMultiThread();
        }
    }

    static void runReactorSingleThread() {
        try {
            ReactorSingleThread reactorSingleThread = new ReactorSingleThread(1234);
            reactorSingleThread.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void runReactorMultiThread() {
        try {
            new Thread(new Reactor(1234)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
