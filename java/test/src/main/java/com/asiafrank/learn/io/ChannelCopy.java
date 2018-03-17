package com.asiafrank.learn.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelCopy {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws Exception {
        String source = "src/com/asiafrank/learn/io/ChannelCopy.java";
        String dest = "src/com/asiafrank/learn/io/testChannelCopy.txt";

        FileChannel in = new FileInputStream(source).getChannel(),
                    out = new FileOutputStream(dest).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        while (in.read(buffer) != -1) {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }
    }
}
