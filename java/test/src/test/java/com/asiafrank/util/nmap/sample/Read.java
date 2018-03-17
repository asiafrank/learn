package com.asiafrank.util.nmap.sample;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Read - Created at 12/12/2016.
 * <p>
 * </p>
 *
 * @author zhangxf
 */
public class Read {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Read{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
