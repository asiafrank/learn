package com.asiafrank.learn.io;

import java.io.File;

/**
 * Created by Xiaofan Zhang on 11/4/2016.
 */
public class MakeDirectories {
    private static void usage() {
        System.err.println(
            "Usage: MakeDirectories path1 ...\n" +
            "Creates each path\n" +
            "Usage: MakeDirectories -d path1 ...\n" +
            "Delete each path\n" +
            "Usage: MakeDirectories -r path1 path2 ...\n" +
            "Renames from path1 to path2"
        );
        System.exit(1);
    }

    private static void fileData(File f) {
        System.out.println(
            "Absolute path: " + f.getAbsolutePath() +
            "\n Can read: " + f.canRead() +
            "\n Can write: " + f.canWrite() +
            "\n getName: " + f.getName() +
            "\n getParent: " + f.getParent() +
            "\n getPath: " + f.getPath() +
            "\n length: " + f.length() +
            "\n lastModified: " + f.lastModified()
        );
    }

    public static void main(String[] args) {
        if (args.length < 1) usage();
        if (args[0].equals("-r")) {
            if (args.length != 3) usage();
            File old = new File(args[1]),
                 rename = new File(args[2]);
            old.renameTo(rename);
            fileData(old);
            fileData(rename);
            return;
        }

        int count = 0;
        boolean del = false;
        if (args[0].equals("-d")) {
            count++;
            del = true;
        }
        count--;
        while (++count < args.length) {
            File f = new File(args[count]);
            if (f.exists()) {
                System.out.println(f + "exists");
                if (del) {
                    System.out.println("deleting..." + f);
                    f.delete();
                }
            } else { // Doesn't exist
                if (!del) {
                    f.mkdirs();
                    System.out.println("created " + f);
                }
            }
            fileData(f);
        }
    }
}
