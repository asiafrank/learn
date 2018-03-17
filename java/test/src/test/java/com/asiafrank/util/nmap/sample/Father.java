package com.asiafrank.util.nmap.sample;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.LinkedList;

@JacksonXmlRootElement(localName = "simple")
public class Father {
    @JacksonXmlProperty(isAttribute = true)
    private int x;

    @JacksonXmlProperty(isAttribute = true)
    private int y;

    // use pojo to wrap different element type
    @JacksonXmlProperty(localName = "children")
    private Children children;

    // wrapped by dreams
    @JacksonXmlElementWrapper(localName = "dreams")
    @JacksonXmlProperty(localName = "dream")
    private LinkedList<Dream> dreams;

    // no wrapper
    @JacksonXmlProperty(localName = "read")
    private LinkedList<Read> reads;

    @JacksonXmlProperty
    private Age age;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Children getChildren() {
        return children;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public LinkedList<Dream> getDreams() {
        return dreams;
    }

    public void setDreams(LinkedList<Dream> dreams) {
        this.dreams = dreams;
    }

    public LinkedList<Read> getReads() {
        return reads;
    }

    public void setReads(LinkedList<Read> reads) {
        this.reads = reads;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Father{" +
                "x=" + x +
                ", y=" + y +
                ", children=" + children +
                ", dreams=" + dreams +
                ", reads=" + reads +
                ", age=" + age +
                '}';
    }
}