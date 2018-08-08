package com.asiafrank.jarcheck;

/**
 * 冲突的 jar 信息
 * src conflict with tgt
 *
 * @author zhangxf created at 8/6/2018.
 */
public class ConflictJar {

    private JarCheckEntry src;
    private JarCheckEntry tgt;

    public ConflictJar(JarCheckEntry src, JarCheckEntry tgt) {
        this.src = src;
        this.tgt = tgt;
    }

    public JarCheckEntry getSrc() {
        return src;
    }

    public void setSrc(JarCheckEntry src) {
        this.src = src;
    }

    public JarCheckEntry getTgt() {
        return tgt;
    }

    public void setTgt(JarCheckEntry tgt) {
        this.tgt = tgt;
    }

    @Override
    public String toString() {
        return src.getJarFileName() + " conflict with " + tgt.getJarFileName();
    }
}
