package com.asiafrank.learn.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Handler implements Runnable {
    final static ExecutorService pool = Executors.newFixedThreadPool(10);

    final SocketChannel socket;
    final SelectionKey sk;

    private final int MAXIN = 1024;
    private final int MAXOUT = 1024;

    ByteBuffer input = ByteBuffer.allocate(MAXIN);
    ByteBuffer output = ByteBuffer.allocate(MAXOUT);

    final static int READING = 0, SENDING = 1;
    final static int PROCESSING = 3;

    int state = READING;

    public Handler(Selector sel, SocketChannel c) throws IOException {
        this.socket = c;
        c.configureBlocking(false);
        // Optionally try first read now
        sk = socket.register(sel, 0);
        sk.interestOps(SelectionKey.OP_READ);
        sk.attach(this);
        sel.wakeup();
    }

    boolean inputIsComplete() {
        // do something
        System.out.println("input is complete");
        return true;
    }

    boolean outputIsComplete() {
        // do something
        System.out.println("output is complete");
        return true;
    }

    void process() {
        // do something
        System.out.println("processing");
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException e) {
            e.printStackTrace();
            sk.cancel();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    synchronized void read() {
        System.out.println("handler read");
        try {
            socket.read(input);
            if (inputIsComplete()) {
                state = PROCESSING;
                pool.execute(new Processer());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send() throws IOException {
        System.out.println("handler write");
        socket.write(output);
        if (outputIsComplete()) {
            sk.cancel();
        }
    }

    synchronized void processAndHandOff() {
        System.out.println("processAndhandOff");
        process();
        state = SENDING; // or rebind attachment
        sk.interestOps(SelectionKey.OP_WRITE);
    }

    class Processer implements Runnable {
        @Override
        public void run() {
            System.out.println("processer");
            processAndHandOff();
        }
    }
}
