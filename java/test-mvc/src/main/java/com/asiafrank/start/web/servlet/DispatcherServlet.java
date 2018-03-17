package com.asiafrank.start.web.servlet;

import com.asiafrank.Config;
import com.asiafrank.start.web.annotation.RequestMapping;
import com.asiafrank.start.web.common.Operation;
import com.asiafrank.start.web.utils.ReflectionUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends javax.servlet.http.HttpServlet {
    private static Map<String, Operation> requestOperationMap = new HashMap<String, Operation>();
    private static String separator = "+";

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("do Init");
        controllerInit();
    }

    private void controllerInit() {
        try {
            Class[] controllers = ReflectionUtils.getControllerClasses(Config.CONTROLLERS_PACKAGE);
            for (Class c : controllers) {
                // deal with class annotation
                Object o = c.newInstance();
                RequestMapping classRM = ReflectionUtils.getRequestMappingAnnotationWithController(c);

                // deal with method annotation
                Method[] allMethods = c.getDeclaredMethods();
                for (Method m : allMethods) {
                    RequestMapping methodRM = ReflectionUtils.getRequestMappingAnnotationWithMethod(m);
                    if (methodRM != null) {
                        Operation op = Operation.newInstance(o, m);
                        putRequestOperation(classRM, methodRM, op);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void putRequestOperation(RequestMapping classRM, RequestMapping methodRM, Operation op) {
        String[] keys = buildKeys(classRM, methodRM);
        for (String key : keys) {
            requestOperationMap.put(key, op);
        }
    }

    private String[] buildKeys(RequestMapping classRM, RequestMapping methodRM) {
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = null;
        if (classRM != null) {
            builder1.append(classRM.value());
        }

        if (methodRM.value().endsWith("/")) {
            builder1.append(methodRM.value());
        } else {
            builder1.append(methodRM.value());
            builder2 = new StringBuilder(builder1.toString() + "/");
        }

        builder1.append(separator).append(methodRM.method());
        if (builder2 != null) {
            builder2.append(separator).append(methodRM.method());
        }

        if (builder2 != null) {
            return new String[]{format(builder1), format(builder2)};
        } else {
            return new String[]{format(builder1)};
        }
    }

    private String format(StringBuilder builder) {
        return builder.toString().replaceAll("//", "/");
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doHead(req, resp);
        System.out.println("do Head");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
        System.out.println("do Options");
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp);
        System.out.println("do Trace");
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        System.out.println("get LastModified");
        return super.getLastModified(req);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do post");
    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        System.out.println("service");

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String key = request.getRequestURI() + separator + request.getMethod().toUpperCase();
        Operation op = requestOperationMap.get(key);
        if (op == null) {
            throw new RuntimeException("No such RequestMapping");
        }
        op.invoke(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("destroy");
    }
}
