package com.asiafrank.util;

import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.IOUtils;

import java.io.*;

/**
 * Converter
 * <p>
 * </p>
 * Created at 22/12/2016.
 *
 * @author asiafrank
 */
public final class CharsetConverter {

    public static void main(String[] args) {
        String file = "D:\\Download\\c.ass";
        String to =  "C:\\Users\\Administrator\\Desktop\\temp\\";
        try {
            transfer(file, to, "utf-8");
        } catch (IOException | TikaException e) {
            e.printStackTrace();
        }
    }

    /**
     * Transfer source file with {@code charset} to another place
     *
     * @param src     source file path
     * @param to      target directory path string
     * @param charset target charset
     * @throws IOException
     * @throws TikaException
     */
    public static void transfer(String src, String to, String charset) throws IOException, TikaException {
        File f = new File(src);
        if (!f.isFile()) {
            throw new IllegalArgumentException("src is not a file");
        }

        File t = new File(to);
        if (!t.isDirectory()) {
            throw new IllegalArgumentException("to is not a directory");
        }

        // File charset detect
        String fileCharset = getFileCharset(src);

        FileInputStream in = null;
        FileOutputStream out = null;
        Reader r = null;
        Writer w = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            in = new FileInputStream(f);
            String outFileName = to + f.getName();
            out = new FileOutputStream(outFileName);

            r = new InputStreamReader(in, fileCharset);
            w = new OutputStreamWriter(out, charset);
            br = new BufferedReader(r);
            bw = new BufferedWriter(w);

            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line + "\n");
            }
            out.flush();
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(bw);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    private static String getFileCharset(String src) throws IOException, TikaException {
        FileInputStream fis = null;
        AutoDetectReader adr = null;
        try {
            fis = new FileInputStream(src);
            adr = new AutoDetectReader(fis);
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return adr.getCharset().name();
    }
}
