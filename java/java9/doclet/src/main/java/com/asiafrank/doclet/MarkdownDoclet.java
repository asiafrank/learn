package com.asiafrank.doclet;

import com.asiafrank.doclet.utils.IOUtils;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * # 基于 Markdown 语法的 Doclet
 * 这是 Markdown 语法的例子。
 * Markdown 与 html 兼容
 *
 * > 如果需要将普通的 java 写法注释转成 Markdown 语法，并输出文件请使用 `model=markdown2` 参数.
 *
 * [Java 注释规范](https://docs.oracle.com/javase/9/docs/specs/doc-comment-spec.html)
 *
 * @author zhangxf created at 4/20/2018.
 */
@SuppressWarnings("deprecation")
public class MarkdownDoclet extends Doclet {

    private static String newline = System.getProperty("line.separator");

    /**
     * ## 开始转成 Markdown 文件
     * 1. 获取 `-output` 参数
     * 2. 以类名作为 md 文件名称，将注释写入文件中
     *
     * @param root RootDoc
     * @return true, success
     */
    public static boolean start(RootDoc root) {
        String[][] opts = root.options();

        String output = null;
        for (String[] opt : opts) {
            if (opt[0].equals("-output")) {
                output = opt[1];
            }
        }

        if (output == null || output.isEmpty()) return false;

        ClassDoc[] classes = root.classes();
        for (ClassDoc cls : classes) {
            File f = new File(output + File.separator + cls.name() + ".md");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                String content = loadClsContent(cls);
                fos.write(content.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(fos);
            }
        }
        return true;
    }

    private static String loadClsContent(ClassDoc cls) {
        StringBuilder sb = new StringBuilder();
        sb.append(cls.commentText());
        sb.append(newline);

        MethodDoc[] methods = cls.methods();
        for (MethodDoc meth : methods) {
            String text = meth.commentText();
            if (text == null || text.isEmpty())
                continue;
            sb.append(text);
            sb.append(newline);
        }
        return sb.toString();
    }

    public static int optionLength(String option) {
        Map<String, Integer> options = new HashMap<>();
        options.put("-output", 2);

        Integer value = options.get(option);
        return Objects.requireNonNullElse(value, 0);
    }
}
