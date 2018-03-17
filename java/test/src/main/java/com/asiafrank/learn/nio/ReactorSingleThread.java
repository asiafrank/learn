package com.asiafrank.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ReactorSingleThread implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocket;

    // ==========================================
    // Setup
    // ==========================================

    public ReactorSingleThread(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    /*
    Alternatively, use explicit SPI provider:
      SelectorProvider p = SelectorProvider.provider();
      selector = p.openSelector();
      serverSocket = p.openServerSocketChannel();
     */

    // ==========================================
    // Dispatch loop
    // ==========================================

    @Override
    public void run() { // Normally in a new Thread
        System.out.println("server start");
        try {
            while (!Thread.interrupted()) {
                // This may block for a long time. Upon returning, the
                // selected set contains keys of the ready channels.
                int n = selector.select();

                if (n == 0) {
                    continue; // nothing to do
                }

                Set selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {
                        System.out.println("key is acceptable");
                        dispatch(key);
                    } else if (key.isReadable()) {
                        System.out.println("key is readable");
                        dispatch(key);
                    } else if (key.isWritable()) {
                        System.out.println("key is writable");
                        dispatch(key);
                    }
                }
                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("server end");
        }
    }

    void dispatch(SelectionKey k) {
        System.out.println("dispatching");
        Object o = k.attachment();
        Runnable r = (Runnable)o;
        if (r != null) {
            r.run();
        }
    }

    // ===================================D=======
    // Acceptor
    // ==========================================

    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                System.out.println("accepted");
                if (c != null) {
                    Handler h = new Handler(selector, c);
                    h.prepare(selector);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ==========================================
    // Handler Setup
    // ==========================================

    final class Handler implements Runnable {
        final SocketChannel socket;
        final SelectionKey sk;

        private final int MAXIN = 1024;
        private final int MAXOUT = 1024;

        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);

        static final int READING = 0, SENDING = 1;

        int state = READING;

        public Handler(Selector sel, SocketChannel c) throws IOException {
            System.out.println("init handler");
            this.socket = c;
            c.configureBlocking(false);
            // Optionally try first read now
            sk = socket.register(sel, 0);
        }

        void prepare(Selector sel) {
            sk.interestOps(SelectionKey.OP_READ);
            sk.attach(this);
            sel.wakeup();
        }

        boolean inputIsComplete() {
            // do something
            System.out.println("input is complete");
            input.flip();
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
            output.put("your input: ".getBytes());
            for (;;) {
                if (input.hasRemaining() && output.hasRemaining()) {
                    output.put(input.get());
                } else {
                    break;
                }
            }
            output.flip();
        }

        // ==========================================
        // Request Handling
        // ==========================================

        @Override
        public void run() {
            System.out.println("handling");
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

        void read() throws IOException {
            System.out.println("handling read");
            socket.read(input);
            if (inputIsComplete()) {
                process();
                state = SENDING;
                // Normally also do first write now
                sk.interestOps(SelectionKey.OP_WRITE);
            }
        }

        void send() throws IOException {
            System.out.println("handling write");
            socket.write(output);
            input.clear();
            output.clear();
            if (outputIsComplete()) {
//                sk.cancel();
//                socket.finishConnect();
                state = READING;
                sk.interestOps(SelectionKey.OP_READ);
            }
        }
    }
}
