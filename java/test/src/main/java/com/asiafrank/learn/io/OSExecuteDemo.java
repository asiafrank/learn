package com.asiafrank.learn.io;

public class OSExecuteDemo {
    public static void main(String[] args) {
        String absPathPrefix = "/Users/asiafrank/workspace/personal/java/learn/out/production/learn/";
        OSExecute.command("javap " + absPathPrefix + "com/asiafrank/learn/io/OSExecuteDemo.class");
    }
}
