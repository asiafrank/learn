package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Times
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Times {
    @JacksonXmlProperty(isAttribute = true)
    private int srtt;

    @JacksonXmlProperty(isAttribute = true)
    private int rttvar;

    @JacksonXmlProperty(isAttribute = true)
    private int to;

    public int getSrtt() {
        return srtt;
    }

    public void setSrtt(int srtt) {
        this.srtt = srtt;
    }

    public int getRttvar() {
        return rttvar;
    }

    public void setRttvar(int rttvar) {
        this.rttvar = rttvar;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
