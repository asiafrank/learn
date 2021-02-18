package com.asiafrank.java11;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIODemo {

    public static void main(String[] args) throws IOException {
        // scc = ServerSocketChannel.open()
        // selector = Selector.open()
        // scc.register(selector, SelectionKey.OP_ACCEPT)
        // selector.select();


        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 5000));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer readBuff = ByteBuffer.allocate(128);
        ByteBuffer writeBuff = ByteBuffer.allocate(128);
        writeBuff.put("received".getBytes());
        writeBuff.flip(); // make buffer ready for reading

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isAcceptable()) {
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    readBuff.clear(); // make buffer ready for writing
                    socketChannel.read(readBuff);
                    readBuff.flip(); // make buffer ready for reading
                    System.out.println(new String(readBuff.array()));
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    writeBuff.rewind(); // sets the position back to 0
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuff);
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }
}
