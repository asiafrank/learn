package com.asiafrank.start.web.utils;

import com.asiafrank.start.web.annotation.Annotations;
import com.asiafrank.start.web.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class ReflectionUtils {

    public static Class[] getControllerClasses(String packageName)
            throws ClassNotFoundException, IOException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findControllerClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findControllerClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findControllerClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                Class obj = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                if (obj.isAnnotationPresent(Annotations.Controller)) {
                    classes.add(obj);
                }
            }
        }
        return classes;
    }

    public static RequestMapping getRequestMappingAnnotationWithController(Class c) {
        if (c.isAnnotationPresent(Annotations.RequestMapping)) {
            Annotation rm = c.getAnnotation(Annotations.RequestMapping);
            return (RequestMapping)rm;
        }
        return null;
    }

    public static RequestMapping getRequestMappingAnnotationWithMethod(Method m) {
        if (m.isAnnotationPresent(Annotations.RequestMapping)) {
            Annotation rm = m.getAnnotation(Annotations.RequestMapping);
            return (RequestMapping)rm;
        }
        return null;
    }
}
