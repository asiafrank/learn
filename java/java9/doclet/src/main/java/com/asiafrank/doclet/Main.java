package com.asiafrank.doclet;

import java.io.File;

/**
 * 因为 Java9 没有提供对外的变成接口，所以沿用 java8 的方式.
 *
 * Java Standand Doclet: https://docs.oracle.com/javase/9/docs/specs/doc-comment-spec.html
 * @author zhangxf created at 4/20/2018.
 */
@SuppressWarnings("deprecation")
public class Main {

    /**
     * @param args output 文件输出目录，model 是 javadoc 输出，还是 markdown 输出。
     *             例：output=F:\doc model=javadoc
     *                 output=F:\doc model=markdown
     */
    public static void main(String[] args) {
        if (args == null || args.length <= 0) {
            System.exit(1);
        }

        for (String arg : args) {
            System.out.println(arg);
        }

        String model = "markdown";
        String output = "F:\\doc";

        String userDir = System.getProperty("user.dir");
        String srcPath = userDir + File.separator +
                         "doclet" + File.separator +
                         "src" + File.separator +
                         "main" + File.separator +
                         "java";

        String[] args0 = null;
        if (model.equals("markdown")) {
            args0 = new String[] {
                    "-sourcepath", srcPath,
                    "-encoding", "UTF-8",
                    "com.asiafrank.doclet",
                    "-output", output
            };
        } else {
            args0 = new String[] {
                    "-d", output,
                    "-sourcepath", srcPath,
                    "-encoding", "UTF-8",
                    "com.asiafrank.doclet",
            };
        }

        com.sun.tools.javadoc.Main.execute(args0);
    }
}
