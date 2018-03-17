package com.asiafrank.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocket;

    // ==========================================
    // Setup
    // ==========================================

    public Reactor(int port) throws IOException {
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
        try {
            while (!Thread.interrupted()) {
                // This may block for a long time. Upon returning, the
                // selected set contains keys of the ready channels.
                int n = selector.select();

                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {
                        dispatch(key);
                    } else if (key.isReadable()) {
                        dispatch(key);
                    } else if (key.isWritable()) {
                        dispatch(key);
                    }
                }
                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)k.attachment();
        if (r != null) {
            r.run();
        }
    }

    // ==========================================
    // Acceptor
    // ==========================================

    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null) {
                    new Handler(selector, c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
