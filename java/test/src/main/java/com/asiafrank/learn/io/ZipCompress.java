package com.asiafrank.learn.io;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

import static net.mindview.util.Print.*;

public class ZipCompress {
    public static void main(String[] args) throws IOException {
        String prefix = "src/com/asiafrank/learn/io/";
        String[] srcs = {
                prefix + "LargeMappedFiles.java",
                prefix + "GetData.java",
                prefix + "Endians.java"
        };

        String zipFileName = prefix + "test.zip";
        FileOutputStream f = new FileOutputStream(zipFileName);
        CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
        ZipOutputStream zos = new ZipOutputStream(csum);
        BufferedOutputStream out = new BufferedOutputStream(zos);
        zos.setComment("A test of Java Zipping");
        // No corresponding getComment(), though.

        for (String s : srcs) {
            print("Writing file " + s);
            BufferedReader in = new BufferedReader(new FileReader(s));
            zos.putNextEntry(new ZipEntry(s));
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();
            out.flush();
        }
        out.close();

        // Checksum valid only after the file has been closed!
        print("Checksum: " + csum.getChecksum().getValue());

        // Now extract the files
        print("Reading file");
        FileInputStream fi = new FileInputStream(zipFileName);
        CheckedInputStream csumi = new CheckedInputStream(fi, new Adler32());
        ZipInputStream in2 = new ZipInputStream(csumi);
        BufferedInputStream bis = new BufferedInputStream(in2);
        ZipEntry ze;
        while ((ze = in2.getNextEntry()) != null) {
            print("Reading file " + ze);
            int x;
            while ((x = bis.read()) != -1) {
                System.out.write(x);
            }
        }

        if (srcs.length == 1) {
            print("Checksum: " + csumi.getChecksum().getValue());
        }
        bis.close();

        // Alternative way to open and read Zip files:
        ZipFile zf = new ZipFile(zipFileName);
        Enumeration e = zf.entries();
        while (e.hasMoreElements()) {
            ZipEntry ze2 = (ZipEntry)e.nextElement();
            print("File: " + ze2);
            // ... and extract the data as before
        }
    }
}
