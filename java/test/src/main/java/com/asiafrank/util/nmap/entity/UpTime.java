package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * UpTime
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class UpTime {
    @JacksonXmlProperty(isAttribute = true)
    private long seconds;

    @JacksonXmlProperty(localName = "lastboot", isAttribute = true)
    private String lastBoot;

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public String getLastBoot() {
        return lastBoot;
    }

    public void setLastBoot(String lastBoot) {
        this.lastBoot = lastBoot;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
