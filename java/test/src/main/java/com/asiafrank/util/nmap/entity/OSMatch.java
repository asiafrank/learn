package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.LinkedList;

/**
 * OSMatch
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class OSMatch {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private int accuracy;

    @JacksonXmlProperty(isAttribute = true)
    private int line;

    @JacksonXmlProperty(localName = "osclass")
    private LinkedList<OSClass> osClasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public LinkedList<OSClass> getOsClasses() {
        return osClasses;
    }

    public void setOsClasses(LinkedList<OSClass> osClasses) {
        this.osClasses = osClasses;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
