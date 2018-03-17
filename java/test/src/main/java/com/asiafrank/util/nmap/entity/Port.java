package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.LinkedList;

public class Port {
    @JacksonXmlProperty(localName = "protocol", isAttribute = true)
    private String protocol;

    @JacksonXmlProperty(localName = "portid", isAttribute = true)
    private int portId;

    @JacksonXmlProperty(localName = "state")
    private State state;

    @JacksonXmlProperty(localName = "service")
    private Service service;

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
