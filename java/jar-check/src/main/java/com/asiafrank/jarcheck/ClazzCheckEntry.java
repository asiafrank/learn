package com.asiafrank.jarcheck;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * @author zhangxf created at 8/6/2018.
 */
public class ClazzCheckEntry implements Cloneable {

    /**
     * absolute jar path
     */
    private String jar;

    /**
     * JarEntry.name
     *
     * a.clazzName = b.clazzName 表示类冲突
     */
    private String clazzName;

    public ClazzCheckEntry() {
    }

    public ClazzCheckEntry(@NotNull File jar, @NotNull String clazzName) {
        assert jar != null;
        assert clazzName != null && !clazzName.isEmpty();

        this.jar = jar.getAbsolutePath();
        this.clazzName = clazzName;
    }

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClazzCheckEntry that = (ClazzCheckEntry) o;
        return Objects.equals(jar, that.jar) &&
               Objects.equals(clazzName, that.clazzName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jar, clazzName);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            ClazzCheckEntry clone = new ClazzCheckEntry();
            clone.setJar(jar);
            clone.setClazzName(clazzName);
            return clone;
        }
    }
}
