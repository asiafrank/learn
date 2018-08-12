package com.asiafrank.jarcheck;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * 检测时需要的 jar 信息
 *
 * @author zhangxf created at 8/6/2018.
 */
public class JarCheckEntry implements Cloneable {

    /**
     * jar 文件的绝对路径
     */
    private String absolutePath;

    /**
     * jar 文件名称
     */
    private String jarFileName;

    /**
     * INDEX.LIST 中第一行表示的 jar 名称。
     * 当 INDEX.LIST 不存在时，jarName = jarFileName
     *
     * a.jarName = b.jarName 表示冲突
     */
    private String jarName;

    /**
     * META-INF/MANIFEST.MF 中按以下优先级获取：
     * 1.Bundle-Version
     * 2.Implementation-Version
     * 3.Specification-Version
     * 4.jarFileName 中截取
     */
    private String version;

    public JarCheckEntry() {
    }

    public JarCheckEntry(@NotNull File jar) {
        assert jar != null;

        this.absolutePath = jar.getAbsolutePath();
        this.jarFileName = jar.getName();
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getJarFileName() {
        return jarFileName;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JarCheckEntry that = (JarCheckEntry) o;
        return Objects.equals(absolutePath, that.absolutePath) &&
               Objects.equals(jarFileName, that.jarFileName) &&
               Objects.equals(jarName, that.jarName) &&
               Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(absolutePath, jarFileName, jarName, version);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            JarCheckEntry clone = new JarCheckEntry();
            clone.setAbsolutePath(absolutePath);
            clone.setJarFileName(jarFileName);
            clone.setJarName(jarName);
            clone.setVersion(version);
            return clone;
        }
    }
}
