package com.asiafrank.learn.springboot.filter;

import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * 参数敏感字符校验
 * @author zhangxf created at 4/23/2018.
 */
@WebFilter(filterName = "/PermissionFilter",
           urlPatterns ={ "/*"},
           asyncSupported=true)
public class CommonFilter implements Filter {

    private static String[] blockURIs = {
            "/tmp-file"
    };

    private static String[] excludeURIs = {
            "/sample"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException
    {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;

            String uri = req.getRequestURI();

            for (String u : blockURIs) {
                if (u.equals(uri)) {
                    responseErrorMsg(res, "Permission deny");
                    return;
                }
            }

            boolean exclude = false;
            for (String u : excludeURIs) {
                if (u.equals(uri)) {
                    exclude = true;
                }
            }

            if (!exclude) {
                /* 校验入参是否存在注入等问题 */
                String queryStr = req.getQueryString();
                String cookieStr = getCookieString(req);

                Set<String> params = new HashSet<>();
                if (queryStr != null) {
                    params.add(queryStr);
                }
                params.add(cookieStr);

                if (req.getHeader("Content-Type") != null && req.getHeader("Content-Type").startsWith("application/json")) {
                    String body = IOUtils.toString(request.getInputStream(), "UTF-8");
                    req = new CopyRequest(req, body.getBytes());
                    params.add(body);
                }

                for (String p : params) {
                    if (InjectionBlocker.match(p)) {
                        responseErrorMsg(res, "bad characters");
                        return;
                    }
                }
            }

            chain.doFilter(req, response);
        }
    }

    private void responseErrorMsg(HttpServletResponse res, String msg) throws IOException {
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json;charset=utf-8");
        res.setStatus(403);

        PrintWriter pw = res.getWriter();
        pw.write(msg);
    }

    private String getCookieString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                sb.append(cookies[i].getName()).append("=").append(cookies[i].getValue());
                if (i < cookies.length - 1) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public void destroy() {

    }

    private class CopyRequest extends HttpServletRequestWrapper {

        private ServletInputStream inputStream;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        public CopyRequest(HttpServletRequest request, byte[] data) {
            super(request);
            inputStream = new CopyInputStream(data);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return inputStream;
        }
    }

    private class CopyInputStream extends ServletInputStream {

        private ByteArrayInputStream is;

        public CopyInputStream(byte[] data) {
            this.is = new ByteArrayInputStream(data);
        }

        @Override
        public int read() throws IOException {
            return is.read();
        }

        @Override
        public boolean isFinished() {
            return is.available() <= 0;
        }

        @Override
        public boolean isReady() {
            return !isFinished();
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }
    }
}
