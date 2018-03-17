package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * IPIDSequence
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class IPIDSequence {
    @JacksonXmlProperty(localName = "class", isAttribute = true)
    private String clazz;

    @JacksonXmlProperty(localName = "values", isAttribute = true)
    private String values;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
