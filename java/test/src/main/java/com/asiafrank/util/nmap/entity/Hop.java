package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Hop
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Hop {
    @JacksonXmlProperty(localName = "ttl", isAttribute = true)
    private int ttl;

    @JacksonXmlProperty(localName = "ipaddr", isAttribute = true)
    private String ipAddr;

    @JacksonXmlProperty(localName = "rtt", isAttribute = true)
    private double rtt;

    @JacksonXmlProperty(localName = "host", isAttribute = true)
    private String host;

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public double getRtt() {
        return rtt;
    }

    public void setRtt(double rtt) {
        this.rtt = rtt;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
