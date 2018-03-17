package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * PortUsed
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class PortUsed {
    @JacksonXmlProperty(localName = "state", isAttribute = true)
    private String state;

    @JacksonXmlProperty(localName = "proto", isAttribute = true)
    private String protocol;

    @JacksonXmlProperty(localName = "portid", isAttribute = true)
    private int portId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
