package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Status of {@link Host}
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Status {
    @JacksonXmlProperty(isAttribute = true)
    private String state;

    @JacksonXmlProperty(isAttribute = true)
    private String reason;

    @JacksonXmlProperty(localName = "reason_ttl",isAttribute = true)
    private int reasonTTL;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getReasonTTL() {
        return reasonTTL;
    }

    public void setReasonTTL(int reasonTTL) {
        this.reasonTTL = reasonTTL;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
