package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Elem of {@link Script}
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Elem {
    @JacksonXmlProperty(isAttribute = true)
    private String key;

    @JacksonXmlText
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
