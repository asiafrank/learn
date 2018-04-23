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
     *             例：output=F:\doc model=javadoc package=com.asiafrank.doclet
     *                 output=F:\doc model=markdown package=com.asiafrank.doclet
     */
    public static void main(String[] args) {
        if (args == null || args.length <= 0) {
            System.exit(1);
        }
        com.sun.tools.javadoc.Main.execute(buildArgs(args));
    }

    private static String[] buildArgs(String[] args) {
        final String output_pre = "output=";
        final String model_pre  = "model=";
        final String pkg_pre    = "package=";

        String output = "";
        String model  = "";
        String pkg    = "";
        for (String arg : args) {
            if (arg.startsWith(output_pre))
                output = arg.substring(output_pre.length());
            if (arg.startsWith(model_pre))
                model = arg.substring(model_pre.length());
            if (arg.startsWith(pkg_pre))
                pkg = arg.substring(pkg_pre.length());
        }

        String userDir = System.getProperty("user.dir");
        String srcPath = userDir  + File.separator +
                         "doclet" + File.separator +
                         "src"    + File.separator +
                         "main"   + File.separator +
                         "java";

        if ("markdown".equals(model)) {
            return new String[]{
                    "-doclet", MarkdownDoclet.class.getCanonicalName(),
                    "-sourcepath", srcPath,
                    "-encoding", "UTF-8",
                    pkg,
                    "-output", output
            };
        } else {
            return new String[] {
                    "-d", output,
                    "-sourcepath", srcPath,
                    "-encoding", "UTF-8",
                    pkg,
            };
        }
    }
}
