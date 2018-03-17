package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.LinkedList;

/**
 * OS of {@link Port}
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class OS {
    @JacksonXmlProperty(localName = "portused")
    private LinkedList<PortUsed> portUseds;

    @JacksonXmlProperty(localName = "osmatch")
    private LinkedList<OSMatch> osMatches;

    public LinkedList<PortUsed> getPortUseds() {
        return portUseds;
    }

    public void setPortUseds(LinkedList<PortUsed> portUseds) {
        this.portUseds = portUseds;
    }

    public LinkedList<OSMatch> getOsMatches() {
        return osMatches;
    }

    public void setOsMatches(LinkedList<OSMatch> osMatches) {
        this.osMatches = osMatches;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
