package com.asiafrank.learn.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {
    private static final int BSIZE = 1024;
    private static final String filePath = "src/com/asiafrank/learn/io/data.txt";
    public static void main(String[] args) throws Exception {
        // Write a file:
        FileChannel fc = new FileOutputStream(filePath).getChannel();
        fc.write(ByteBuffer.wrap("Some text ".getBytes()));
        fc.close();

        // Add to the end of the file:
        fc = new RandomAccessFile(filePath, "rw").getChannel();
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("Some more".getBytes()));
        fc.close();

        // Read the file:
        fc = new FileInputStream(filePath).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();
        while (buff.hasRemaining()) {
            System.out.print((char)buff.get());
        }
    }
}
