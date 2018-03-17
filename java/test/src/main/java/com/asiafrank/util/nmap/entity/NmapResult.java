package com.asiafrank.util.nmap.entity;

import com.asiafrank.util.nmap.deserializer.UnixTimestampDeserializer;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Date;
import java.util.LinkedList;

@JacksonXmlRootElement(localName = "nmaprun")
public class NmapResult {
    @JacksonXmlProperty(localName = "scanner", isAttribute = true)
    private String scanner;

    @JacksonXmlProperty(localName = "args", isAttribute = true)
    private String args;

    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    @JacksonXmlProperty(localName = "start", isAttribute = true)
    private Date start;

    @JacksonXmlProperty(localName = "startstr", isAttribute = true)
    private String startStr;

    @JacksonXmlProperty(localName = "version", isAttribute = true)
    private String version;

    @JacksonXmlProperty(localName = "xmloutputversion", isAttribute = true)
    private String xmlOutputVersion;

    @JacksonXmlProperty(localName = "scaninfo")
    private ScanInfo scanInfo;

    @JacksonXmlProperty(localName = "verbose")
    private Verbose verbose;

    @JacksonXmlProperty(localName = "debugging")
    private Debugging debugging;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "host")
    private LinkedList<Host> hosts;

    // Ignore PostScript

    @JacksonXmlProperty(localName = "runstats")
    private RunStats runStats;

    public String getScanner() {
        return scanner;
    }

    public void setScanner(String scanner) {
        this.scanner = scanner;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getStartStr() {
        return startStr;
    }

    public void setStartStr(String startStr) {
        this.startStr = startStr;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getXmlOutputVersion() {
        return xmlOutputVersion;
    }

    public void setXmlOutputVersion(String xmlOutputVersion) {
        this.xmlOutputVersion = xmlOutputVersion;
    }

    public ScanInfo getScanInfo() {
        return scanInfo;
    }

    public void setScanInfo(ScanInfo scanInfo) {
        this.scanInfo = scanInfo;
    }

    public Verbose getVerbose() {
        return verbose;
    }

    public void setVerbose(Verbose verbose) {
        this.verbose = verbose;
    }

    public Debugging getDebugging() {
        return debugging;
    }

    public void setDebugging(Debugging debugging) {
        this.debugging = debugging;
    }

    public LinkedList<Host> getHosts() {
        return hosts;
    }

    public void setHosts(LinkedList<Host> hosts) {
        this.hosts = hosts;
    }

    public RunStats getRunStats() {
        return runStats;
    }

    public void setRunStats(RunStats runStats) {
        this.runStats = runStats;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
