package com.asiafrank.doclet.utils;

import java.io.IOException;
import java.io.OutputStream;

public final class IOUtils {
    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
