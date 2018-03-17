package com.asiafrank.learn.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class BufferToText {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws Exception {
        String fileName = "src/com/asiafrank/learn/io/data2.txt";
        FileChannel fc = new FileOutputStream(fileName).getChannel();
        fc.write(ByteBuffer.wrap("I love english".getBytes()));
        fc.close();

        fc = new FileInputStream(fileName).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();

        // Doesn't work:
        System.out.println(buff.asCharBuffer());

        // Decode using this system's default Charset:
        buff.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using " + encoding + ": " + Charset.forName(encoding).decode(buff));

        // Or, we could encode with something that will print:
        fc = new FileOutputStream(fileName).getChannel();
        fc.write(ByteBuffer.wrap("Are you kidding?".getBytes("UTF-16BE")));
        fc.close();

        // Now try reading again:
        fc = new FileInputStream(fileName).getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());

        // Use a CharBuffer to write through
        fc = new FileOutputStream(fileName).getChannel();
        buff = ByteBuffer.allocate(24);
        buff.asCharBuffer().put("No kidding");
        fc.write(buff);
        fc.close();

        // Read and display:
        fc = new FileInputStream(fileName).getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
    }
}
