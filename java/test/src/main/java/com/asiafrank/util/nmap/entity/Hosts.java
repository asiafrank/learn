package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Hosts of {@link RunStats}
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Hosts {
    @JacksonXmlProperty(isAttribute = true)
    private int up;

    @JacksonXmlProperty(isAttribute = true)
    private int down;

    @JacksonXmlProperty(isAttribute = true)
    private int total;

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
