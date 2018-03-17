package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.LinkedList;

/**
 * Trace
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Trace {
    @JacksonXmlProperty(isAttribute = true)
    private int port;

    @JacksonXmlProperty(localName = "proto", isAttribute = true)
    private String protocol;

    @JacksonXmlProperty
    private LinkedList<Hop> hop;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public LinkedList<Hop> getHop() {
        return hop;
    }

    public void setHop(LinkedList<Hop> hop) {
        this.hop = hop;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
