package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Ports
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Ports {

    @JacksonXmlProperty(localName = "extraports")
    private LinkedList<ExtraPorts> extraPorts;

    @JacksonXmlProperty(localName = "port")
    private List<Port> port;

    public LinkedList<ExtraPorts> getExtraPorts() {
        return extraPorts;
    }

    public void setExtraPorts(LinkedList<ExtraPorts> extraPorts) {
        this.extraPorts = extraPorts;
    }

    public List<Port> getPort() {
        return port;
    }

    public void setPort(List<Port> port) {
        this.port = port;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
