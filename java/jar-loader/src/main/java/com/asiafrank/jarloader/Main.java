package com.asiafrank.jarloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * NOTE: jar 包设置为 provided. 单元测试时 classpath 仍能拿到类，不能说明问题，所以放在这里调用 main 方法来做例子
 *
 * @author zhangxf created at 8/7/2018.
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    // maven repository
    private static String mvnRepo = "D:\\repositories";

    private static String[] jarPaths = new String[4];

    static {
        jarPaths[0] = mvnRepo + "\\com\\fasterxml\\jackson\\core\\jackson-annotations\\2.9.0\\jackson-annotations-2.9.0.jar";
        jarPaths[1] = mvnRepo + "\\com\\fasterxml\\jackson\\core\\jackson-core\\2.9.0\\jackson-core-2.9.0.jar";
        jarPaths[2] = mvnRepo + "\\com\\fasterxml\\jackson\\core\\jackson-databind\\2.9.0\\jackson-databind-2.9.0.jar";
        jarPaths[3] = mvnRepo + "\\com\\fasterxml\\jackson\\datatype\\jackson-datatype-jdk8\\2.9.0\\jackson-datatype-jdk8-2.9.0.jar";
    }

    public static void main(String[] args) throws Exception {
        testThreadContextClassLoader();
        testClassLoaderInGroovyEngine();
    }

    /**
     * 这里只能通过反射 newInstance 得到实例，而且要想调用方法也只能通过 Method.invoke 来做，
     * 因为编译好的字节码中，new 关键字去新建实例，会先初始化类定义，其使用的是最初的 Application ClassLoader
     * 而不是自定义的 ClassLoader，即使使用 Thread.currentThread().setContextClassLoader(runtimeLoader); 也没有效果。
     * 如果要做到通过 new 关键字来得到实例只能使用修改字节码的办法。通常都是在启动 JVM 时，定义 javaagent 参数，
     * 然后通过这个 javaagent 程序动态加载 jar，更新类定义。
     *
     * https://stackoverflow.com/questions/42297568/how-to-use-custom-classloader-to-new-object-in-java
     * https://github.com/HotswapProjects/HotswapAgent
     */
    @SuppressWarnings("unchecked")
    private static void testThreadContextClassLoader() throws Exception {
        log.info("============= testThreadContextClassLoader ==================");
        final String json = "{\"id\": 10, \"name\": \"test obj\"}";

        Thread mainThread = Thread.currentThread();
        JarRuntimeLoader runtimeLoader = new JarRuntimeLoader(new URL[]{}, mainThread.getContextClassLoader());
        for (String p : jarPaths) {
            runtimeLoader.addFile(p);
        }
        mainThread.setContextClassLoader(runtimeLoader);

        Thread t = new Thread(()->{
            try {
                Class cl = runtimeLoader.loadClass("com.fasterxml.jackson.databind.ObjectMapper");
                Object mapper0 = cl.newInstance();
                Method m = cl.getMethod("readValue", String.class, Class.class);
                TestObj obj0 = (TestObj)m.invoke(mapper0, json, TestObj.class);
                log.info("reflection, read json: {}", obj0);

                ObjectMapper mapper = new ObjectMapper();
                // 这里 new 关键字使用的是原来的 System Class Loader(Application Class Loader)
                // 因此会报 NoClassDefFoundError。 除非你把这段代码放在单独的 jar 里（定义好 Main-Class），
                // 然后统一由 runtimeLoader 将这个 jar 加载进来，使用 runtimeLoader.invokeClass() 方法调用 这个 jar
                // 的 main 方法。

                TestObj obj = mapper.readValue(json, TestObj.class);
                log.info("use new keyword, read json: {}", obj);
            } catch (Throwable e) {
                log.error("thread load class failed", e);
            }
        });

        t.setContextClassLoader(runtimeLoader);
        t.start();
        t.join();

        log.info("============= testThreadContextClassLoader failed ==================");
    }

    /**
     * 对于 GroovyScriptEngine 来说，指定自定义的 classloader 新建其实例，能做到在 groovy 脚本中使用 new 关键字能正常初始化。
     * 因为 Groovy 的对脚本的解析引擎，在检测到 new 关键字是加载类定义所使用的 classloader 就是 GroovyScriptEngine 实例
     * 指定的 classloader。
     */
    private static void testClassLoaderInGroovyEngine() throws Exception {
        log.info("============= testClassLoaderInGroovyEngine ==================");
        JarRuntimeLoader runtimeLoader = new JarRuntimeLoader(new URL[]{}, Main.class.getClassLoader());
        for (String p : jarPaths) {
            runtimeLoader.addFile(p, true);
        }

        String root;
        String scriptName = "sample.groovy";
        URL url = runtimeLoader.getResource(scriptName);
        if (url == null) {
            log.warn("not found sample.groovy");
            return;
        }

        File f = new File(url.getFile());
        root = f.getParent();

        GroovyScriptEngine scriptEngine = new GroovyScriptEngine(new String[]{root}, runtimeLoader);
        Binding binding = new Binding();
        Script s  = scriptEngine.createScript(scriptName, binding);
        s.invokeMethod("scriptMain", null);
        log.info("============= testClassLoaderInGroovyEngine Success ! ==================");
    }
}
