package com.asiafrank.learn;

import java.io.*;
import java.nio.file.*;

/**
 * Test
 * <p>
 * </p>
 * Created at 22/12/2016.
 *
 * @author asiafrank
 */
public class Convert_GBK_TO_UTF8 {

    public static void main(String[] args) {
        String path = "/Users/asiafrank/Downloads/GB";
        String base = "/Users/asiafrank/Downloads/out/";

        File source = new File(path);
        File[] asses =  source.listFiles();
        if (asses != null) {
            for (File f : asses) {
                convert(f, base);
            }
        }
    }

    private static void convert(File f, String base) {
        FileInputStream in = null;
        FileOutputStream out = null;
        Reader r = null;
        Writer w = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            in = new FileInputStream(f);
            String outFileName = base + f.getName();
            out = new FileOutputStream(outFileName);

            r = new InputStreamReader(in, "gbk");
            w = new OutputStreamWriter(out, "utf-8");
            br = new BufferedReader(r);
            bw = new BufferedWriter(w);

            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line + "\n");
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
