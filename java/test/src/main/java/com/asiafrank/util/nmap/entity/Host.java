package com.asiafrank.util.nmap.entity;

import com.asiafrank.util.nmap.deserializer.UnixTimestampDeserializer;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;
import java.util.LinkedList;

public class Host {
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    @JacksonXmlProperty(localName = "starttime", isAttribute = true)
    private Date startTime;

    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    @JacksonXmlProperty(localName = "endtime", isAttribute = true)
    private Date endTime;

    @JacksonXmlProperty(localName = "status")
    private Status status;

    @JacksonXmlProperty(localName = "address")
    private LinkedList<Address> address;

    @JacksonXmlElementWrapper(localName = "hostnames")
    @JacksonXmlProperty(localName = "hostname")
    private LinkedList<HostName> hostNames;

    @JacksonXmlProperty(localName = "ports")
    private Ports ports;

    @JacksonXmlProperty(localName = "os")
    private OS os;

    @JacksonXmlProperty(localName = "uptime")
    private UpTime upTime;

    @JacksonXmlProperty(localName = "distance")
    private Distance distance;

    @JacksonXmlProperty(localName = "trace")
    private Trace trace;

    @JacksonXmlProperty(localName = "times")
    private Times times;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LinkedList<Address> getAddress() {
        return address;
    }

    public void setAddress(LinkedList<Address> address) {
        this.address = address;
    }

    public LinkedList<HostName> getHostNames() {
        return hostNames;
    }

    public void setHostNames(LinkedList<HostName> hostNames) {
        this.hostNames = hostNames;
    }

    public Ports getPorts() {
        return ports;
    }

    public void setPorts(Ports ports) {
        this.ports = ports;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public UpTime getUpTime() {
        return upTime;
    }

    public void setUpTime(UpTime upTime) {
        this.upTime = upTime;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Trace getTrace() {
        return trace;
    }

    public void setTrace(Trace trace) {
        this.trace = trace;
    }

    public Times getTimes() {
        return times;
    }

    public void setTimes(Times times) {
        this.times = times;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
