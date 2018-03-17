package com.asiafrank.util.nmap.sample;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Arrays;

/**
 * Children - Created at 12/12/2016.
 * <p>
 * </p>
 *
 * @author zhangxf
 */
public class Children {
    @JacksonXmlProperty(localName = "anotherchild")
    private AnotherChild[] anotherChild;

    @JacksonXmlProperty(localName = "child")
    private Child[] child;

    public AnotherChild[] getAnotherChild() {
        return anotherChild;
    }

    public void setAnotherChild(AnotherChild[] anotherChild) {
        this.anotherChild = anotherChild;
    }

    public Child[] getChild() {
        return child;
    }

    public void setChild(Child[] child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Children{" +
                "anotherChild=" + Arrays.toString(anotherChild) +
                ", child=" + Arrays.toString(child) +
                '}';
    }
}
