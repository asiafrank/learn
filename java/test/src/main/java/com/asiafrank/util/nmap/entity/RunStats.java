package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * RunStats
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class RunStats {
    @JacksonXmlProperty(localName = "finished")
    private Finished finished;

    @JacksonXmlProperty(localName = "hosts")
    private Hosts hosts;

    public Finished getFinished() {
        return finished;
    }

    public void setFinished(Finished finished) {
        this.finished = finished;
    }

    public Hosts getHosts() {
        return hosts;
    }

    public void setHosts(Hosts hosts) {
        this.hosts = hosts;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
