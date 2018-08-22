package com.asiafrank.jarcheck;

import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

/**
 * 检测 jar 包冲突的，生成冲突报告。
 * 1.检测 MANIFEST.MF 文件中相关的版本号
 * 3.检测包中类重复
 *
 * UN-Thread-Safe
 * REFERENCE:
 * https://docs.oracle.com/javase/tutorial/deployment/jar/index.html
 * https://docs.oracle.com/javase/8/docs/api/java/util/jar/package-summary.html
 *
 * NOTE: 这只是查看 jar 文件的一个例子。JVM 加载 jar 文件时，classpath 写前面的 jar 会优先加载，后面加载的 jar 中有重复的类名会忽略
 *
 * @author zhangxf created at 8/6/2018.
 */
public class JarConflictDetector {

    private static final Logger log = LoggerFactory.getLogger(JarConflictDetector.class);
    private static final String JAR_SUFFIX = ".jar";
    private static final String JAR_SOURCES_SUFFIX = "sources.jar";
    private static final String CLAZZ_SUFFIX = ".class";

    /**
     * 需要检测的目标文件夹
     */
    private Path dir;

    /**
     * 标记是否已经执行完成
     */
    private boolean complete = false;

    private List<JarCheckEntry> jarCheckEntries;

    private List<ClazzCheckEntry> clazzCheckEntries;

    /**
     * 冲突的 jar 信息
     */
    private List<ConflictJar> conflictJars;

    /**
     * 冲突的类名称及其所在的 jar 信息
     */
    private List<ConflictClazz> conflictClazzes;

    /**
     * key: jarName
     * value: JarCheckEntry
     *
     * a.jarName = b.jarName 表示冲突
     */
    private Map<String, JarCheckEntry> jarMap;

    /**
     * key: jarName
     * value: ClazzCheckEntry
     *
     * a.jarName = b.jarName 表示冲突
     */
    private Map<String, ClazzCheckEntry> clazzMap;

    /**
     * 如果分隔符为 "/", 则 ("/foo","bar","gus") 得到的路径为 "/foo/bar/gus"，中间的分隔符会自动补全。
     *
     * @param first 完整路径本身或者完整路径的第一部分
     * @param more  完整路径的其他部分，与 first 一起拼接成完整路径
     */
    public JarConflictDetector(@NotNull String first, String... more) throws NoSuchFileException {
        this.dir = Paths.get(first, more);
        if (!Files.isDirectory(this.dir))
            throw new NoSuchFileException("the path " + this.dir.toString() + " is not a directory");

        jarCheckEntries   = new LinkedList<>();
        clazzCheckEntries = new LinkedList<>();
        conflictJars      = new LinkedList<>();
        conflictClazzes   = new LinkedList<>();

        jarMap   = new HashMap<>();
        clazzMap = new HashMap<>();
    }

    /**
     * 开始检测：
     * 1.遍历并找到文件夹下的所有 .jar 文件
     * 2.遍历过程中收集 MANIFEST.MF 信息，判断 jar 文件本身信息是否冲突
     * 3.检测类是否有冲突
     * 4.将所有冲突封装到 conflictJars 和 conflictClazzes 属性中作为检测报告。
     */
    public void start() {
        try (Stream<Path> pathStream = Files.walk(dir)) {
            pathStream.forEach(path -> {
                File f = path.toFile();
                String fileName = f.getName();

                if (f.isFile()
                    && f.getName().endsWith(JAR_SUFFIX)
                    && !fileName.endsWith(JAR_SOURCES_SUFFIX))
                {
                    if (log.isDebugEnabled())
                        log.debug("jar path {}", path);
                    jarCheck(f);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            complete = true;
        }
    }

    /**
     * 检测 jar 冲突
     * 检测 class 冲突
     */
    private void jarCheck(@NotNull File jar) {
        try (JarFile jarFile = new JarFile(jar)) {
            JarCheckEntry jarCE = new JarCheckEntry(jar);
            jarCheckEntries.add(jarCE);

            //----- read index list --------
            ZipEntry indexListEntry = jarFile.getEntry("META-INF/INDEX.LIST");
            if (indexListEntry != null) {
                InputStream input = jarFile.getInputStream(indexListEntry);
                readIndexList(input, jarCE);
            } else {
                // set default jarName
                jarCE.setJarName(jarCE.getJarFileName());
            }

            //----- read manifest --------
            Manifest mf = jarFile.getManifest();
            if (mf != null) {
                readManifest(mf, jarCE);
            }

            if (jarCE.getVersion() == null)
                // set default version
                jarCE.setVersion(getVersionFromFileName(jarCE.getJarFileName()));

            JarCheckEntry ce = jarMap.get(jarCE.getJarName());
            if (ce != null) {
                ConflictJar cj = new ConflictJar(ce, jarCE);
                conflictJars.add(cj);
            } else {
                jarMap.put(jarCE.getJarName(), jarCE);
            }

            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements())
                clazzCheck(e.nextElement(), jar);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private Queue<Character> queue = new ArrayDeque<>();

    /**
     * "-" 后跟数字就代表这是版本号，截取到 ".jar" 即可。（简单的有限状态机）
     * 如：xyz-1.0.0.RELEASE.jar 则截取结果为 "1.0.0.RELEASE"
     * 简单的
     * @param fileName jar 的文件名
     * @return 截取到的版本号
     */
    private String getVersionFromFileName(@NotNull String fileName) {
        // 去除 .jar, 但保留这个'.'，充当最后一个字符
        String sub = fileName.substring(0, fileName.length() - JAR_SUFFIX.length() + 1);
        char[] arr = sub.toCharArray();
        int i = 0;
        int len = arr.length;
        char prev = '\0';
        for (;i < len; ++i) {
            char c = arr[i];

            Event e = null;
            if (prev == '\0')
                e = Event.NAME_START;
            if (prev == '-'
                && Character.isDigit(c))
                e = Event.VERSION_START;
            if (i == len - 1)
                e = Event.STRING_END;

            eventHandle(e);

            queue.add(c);
            prev = c;
        }
        queue.clear();

        if (log.isDebugEnabled())
            log.debug("fsm.result {}", fsm.result);
        fsm.currItem = fsm_tb[name_startI];
        return fsm.result;
    }

    private void eventHandle(Event e) {
        if (e == null) return;

        FSMItem i = fsm.currItem;
        Event currE = i.event;
        if (currE == e) {
            fsm.result = i.action.apply(null);
            stateTransfer();
        }

        // ignore
    }

    private void stateTransfer() {
        FSMItem i = fsm.currItem;
        State next = i.nextState;
        switch (next) {
            case NORMAL:
                fsm.currItem = fsm_tb[name_startI];
                break;
            case NAME:
                fsm.currItem = fsm_tb[version_startI];
                break;
            case VERSION:
                fsm.currItem = fsm_tb[version_endI];
                break;
        }
    }

    // 状态
    private enum State {
        NORMAL,  // 什么都不是
        NAME,    // jar 名（不包含版本和 .jar 的部分）
        VERSION, // 版本
    }

    // 事件
    private enum Event {
        NAME_START,
        VERSION_START,
        STRING_END,
    }

    private Function<Void, String> NOTHING = v->{
        queue.clear();
        return "";
    };

    private int name_startI    = 0;
    private int version_startI = 1;
    private int version_endI   = 2;

    private FSMItem[] fsm_tb = {
            new FSMItem(Event.NAME_START, State.NORMAL, NOTHING, State.NAME),
            new FSMItem(Event.VERSION_START, State.NAME, NOTHING, State.VERSION),
            new FSMItem(Event.STRING_END, State.VERSION, v->{
                StringBuilder sb = new StringBuilder();
                while (!queue.isEmpty()) {
                    sb.append(queue.poll());
                }
                return sb.toString();
            }, State.NORMAL)
    };

    private FSM fsm = new FSM(fsm_tb[name_startI]);

    private class FSMItem { // 有限状态表
        Event event;
        State currState;
        Function<Void, String> action;
        State nextState;

        FSMItem(Event e, State curr, Function<Void, String> a, State next) {
            event = e;
            currState = curr;
            action = a;
            nextState = next;
        }
    }

    private class FSM {
        FSMItem currItem;
        String result;

        FSM(FSMItem initItem) {
            currItem = initItem;
        }
    }

    /**
     * 获取 INDEX.LIST 中 jar 的名称
     */
    private void readIndexList(InputStream input, JarCheckEntry jarCE) {
        try (InputStreamReader isr = new InputStreamReader(input, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr))
        {
            String line = null;
            int k = 5; // 5 行内没有需要的信息就退出循环
            while (((line = br.readLine()) != null) && k > 0) {
                if (line.endsWith(JAR_SUFFIX)) {
                    jarCE.setJarName(line);
                    break;
                }

                k--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * META-INF/MANIFEST.MF 中取得 version。
     * 按以下优先级获取：
     * 1.Bundle-Version
     * 2.Implementation-Version
     * 3.Specification-Version
     */
    private void readManifest(Manifest mf, JarCheckEntry jarCE) {
        Attributes attrs = mf.getMainAttributes();
        String bundleVersion = attrs.getValue("Bundle-Version");
        if (bundleVersion != null) {
            jarCE.setVersion(bundleVersion);
            return;
        }

        String implVersion = attrs.getValue("Implementation-Version");
        if (implVersion != null) {
            jarCE.setVersion(implVersion);
            return;
        }

        String specVersion = attrs.getValue("Specification-Version");
        if (specVersion != null) {
            jarCE.setVersion(specVersion);
            return;
        }
    }

    private void clazzCheck(JarEntry e, File jar) {
        String name = e.getName();
        if (!name.endsWith(CLAZZ_SUFFIX)) return;
        ClazzCheckEntry clazzCE = new ClazzCheckEntry(jar, name);
        clazzCheckEntries.add(clazzCE);

        ClazzCheckEntry cce = clazzMap.get(name);
        if (cce != null) {
            if (!cce.getJar().equals(jar.getAbsolutePath())) {
                ConflictClazz cc = new ConflictClazz(clazzCE, cce);
                conflictClazzes.add(cc);
            }
        } else {
            clazzMap.put(name, clazzCE);
        }
    }

    public Path getDir() {
        return dir;
    }

    public boolean isComplete() {
        return complete;
    }

    public List<JarCheckEntry> getJarCheckEntries() {
        return jarCheckEntries;
    }

    public List<ClazzCheckEntry> getClazzCheckEntries() {
        return clazzCheckEntries;
    }

    public List<ConflictJar> getConflictJars() {
        return conflictJars;
    }

    public List<ConflictClazz> getConflictClazzes() {
        return conflictClazzes;
    }
}
