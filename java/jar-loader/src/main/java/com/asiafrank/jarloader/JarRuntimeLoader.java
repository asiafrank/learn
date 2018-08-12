package com.asiafrank.jarloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.*;
import java.util.jar.Attributes;

/**
 * https://docs.oracle.com/javase/tutorial/deployment/jar/jarclassloader.html
 *
 * @author zhangxf created at 8/6/2018.
 */
public class JarRuntimeLoader extends URLClassLoader {
    private ClassLoader parent;

    public JarRuntimeLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.parent = parent;
    }

    public JarRuntimeLoader(URL[] urls) {
        super(urls);
        this.parent = null;
    }

    public JarRuntimeLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
        this.parent = parent;
    }

    public void addFile(String path) throws MalformedURLException {
        addFile(path, false);
    }

    public void addFile(String path, boolean forGroovyEngine) throws MalformedURLException {
        String urlPath;
        if (forGroovyEngine) urlPath = "file:" + path;
        else                 urlPath = "jar:file:" + path + "!/";

        addURL (new URL (urlPath));
    }

    //===== other util methods ======

    public String getMainClassName(String url) throws IOException {
        URL u = new URL("jar", "", url + "!/");
        JarURLConnection uc = (JarURLConnection)u.openConnection();
        Attributes attr = uc.getMainAttributes();
        return attr != null
                ? attr.getValue(Attributes.Name.MAIN_CLASS)
                : null;
    }

    @SuppressWarnings("unchecked")
    public void invokeClass(String name, String[] args)
            throws ClassNotFoundException,
                   NoSuchMethodException,
                   InvocationTargetException
    {
        Class c = loadClass(name);
        Method m = c.getMethod("main", args.getClass());
        m.setAccessible(true);
        int mods = m.getModifiers();
        if (m.getReturnType() != void.class || !Modifier.isStatic(mods) ||
            !Modifier.isPublic(mods)) {
            throw new NoSuchMethodException("main");
        }
        try {
            m.invoke(null, new Object[] { args });
        } catch (IllegalAccessException e) {
            // This should not happen, as we have disabled access checks
        }
    }
}
