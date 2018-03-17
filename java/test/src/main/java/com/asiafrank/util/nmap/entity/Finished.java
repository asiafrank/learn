package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

/**
 * Finished of {@link RunStats}
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Finished {

    @JacksonXmlProperty(localName = "time", isAttribute = true)
    private Date time;

    @JacksonXmlProperty(localName = "timestr", isAttribute = true)
    private String timeStr;

    @JacksonXmlProperty(localName = "elapsed", isAttribute = true)
    private double elapsed;

    @JacksonXmlProperty(localName = "summary", isAttribute = true)
    private String summary;

    @JacksonXmlProperty(localName = "exit", isAttribute = true)
    private String exit;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public double getElapsed() {
        return elapsed;
    }

    public void setElapsed(double elapsed) {
        this.elapsed = elapsed;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
