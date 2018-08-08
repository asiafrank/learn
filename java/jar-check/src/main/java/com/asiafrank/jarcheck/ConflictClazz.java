package com.asiafrank.jarcheck;

/**
 * 冲突的类信息
 * src conflict with tgt
 *
 * @author zhangxf created at 8/6/2018.
 */
public class ConflictClazz {

    private ClazzCheckEntry src;
    private ClazzCheckEntry tgt;

    public ConflictClazz(ClazzCheckEntry src, ClazzCheckEntry tgt) {
        this.src = src;
        this.tgt = tgt;
    }

    public ClazzCheckEntry getSrc() {
        return src;
    }

    public void setSrc(ClazzCheckEntry src) {
        this.src = src;
    }

    public ClazzCheckEntry getTgt() {
        return tgt;
    }

    public void setTgt(ClazzCheckEntry tgt) {
        this.tgt = tgt;
    }

    @Override
    public String toString() {
        return src.getJar() + "[" + src.getClazzName() + "] conflict with " +
               tgt.getJar() + "[" + tgt.getClazzName() + "]";
    }
}
