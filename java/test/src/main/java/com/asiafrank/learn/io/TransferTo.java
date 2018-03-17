package com.asiafrank.learn.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class TransferTo {
    public static void main(String[] args) throws Exception {
        String source = "src/com/asiafrank/learn/io/TransferTo.java";
        String dest = "src/com/asiafrank/learn/io/testTransferTo.txt";

        FileChannel in = new FileInputStream(source).getChannel(),
                    out = new FileOutputStream(dest).getChannel();
        in.transferTo(0, in.size(), out);
        // Or:
        // out.transterFrom(in, 0, in.size());
    }
}