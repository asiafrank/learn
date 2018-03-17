package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ExtraReasons {
    @JacksonXmlProperty(localName = "reason", isAttribute = true)
    private String reason;

    @JacksonXmlProperty(localName = "count", isAttribute = true)
    private int count;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
