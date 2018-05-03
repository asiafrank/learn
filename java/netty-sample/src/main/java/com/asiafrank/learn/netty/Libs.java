package com.asiafrank.learn.netty;

import java.net.URL;

/**
 * @author zhangxf created at 2/2/2018.
 */
public class Libs {

    private static ClassLoader cl = Libs.class.getClassLoader();
    private static boolean is_x64;

    static {
        String m = System.getProperty("sun.arch.data.model");
        is_x64 = m.equals("64");
    }

    public static String getLibPath(String libName) {
        URL url = cl.getResource(libName);
        if (url == null)
            throw new IllegalArgumentException("cannot found library: " + libName);
        return url.getPath();
    }

    public static boolean isX64() {
        return is_x64;
    }
}