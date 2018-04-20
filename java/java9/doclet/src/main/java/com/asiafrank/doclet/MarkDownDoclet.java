package com.asiafrank.doclet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

/**
 * 什么都没有
 * 注释规范: https://docs.oracle.com/javase/9/docs/specs/doc-comment-spec.html
 *
 * @author zhangxf created at 4/20/2018.
 */
@SuppressWarnings("deprecation")
public class MarkDownDoclet extends Doclet {
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        for (ClassDoc cls : classes) {
            System.out.println(cls.commentText());
            MethodDoc[] methods = cls.methods();
            for (MethodDoc meth : methods) {
                System.out.println(meth);
            }
        }
        return true;
    }
}
