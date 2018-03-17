package com.asiafrank.start.web.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public final class ResponseUtils {
    public static void print(HttpServletResponse response, String content) {
        PrintWriter out = null;
        try {
            // TODO: should be extended to support json, image or any other format
            response.setContentType("text/html");
            out = response.getWriter();
            out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}