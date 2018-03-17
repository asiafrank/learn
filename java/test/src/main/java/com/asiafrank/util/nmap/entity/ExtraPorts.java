package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.LinkedList;

public class ExtraPorts {
    @JacksonXmlProperty(localName = "state", isAttribute = true)
    private String state;

    @JacksonXmlProperty(localName = "count", isAttribute = true)
    private int count;

    @JacksonXmlProperty(localName = "extrareasons")
    private LinkedList<ExtraReasons> extraReasons;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LinkedList<ExtraReasons> getExtraReasons() {
        return extraReasons;
    }

    public void setExtraReasons(LinkedList<ExtraReasons> extraReasons) {
        this.extraReasons = extraReasons;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
