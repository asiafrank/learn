package com.asiafrank.jol.example;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * https://hg.openjdk.java.net/code-tools/jol/file/tip/jol-samples/src/main/java/org/openjdk/jol/samples/
 */
public class LayoutMarkword {
    public static void main(String[] args) {
        Object o = new Object();

        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
}
