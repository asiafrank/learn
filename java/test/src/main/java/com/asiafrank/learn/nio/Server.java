package com.asiafrank.learn.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int PORT = 1234;
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            while (Thread.interrupted()) {
                new Thread(new Handler(ss.accept())).start();
            }
            // or single-threaded or a thread pool
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Handler implements Runnable {
        final Socket socket;
        private int MAX_INPUT = 1024;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                byte[] input = new byte[MAX_INPUT];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private byte[] process(byte[] b) {
            // do something
            return "data processed".getBytes();
        }
    }
}
