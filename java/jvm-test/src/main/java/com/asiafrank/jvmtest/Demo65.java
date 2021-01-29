package com.asiafrank.jvmtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * dump 出来的文件，用 MAT 分析内存泄漏
 * jps 查找 pid
 * jmap -dump:live,format=b,file=dump.hprof {pid}
 */
public class Demo65 {
    public static void main(String[] args) throws InterruptedException {
        List<Data> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(new Data());
        }

        TimeUnit.HOURS.sleep(1);
    }

    private static class Data {
        private String s = "hello";

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }
}
