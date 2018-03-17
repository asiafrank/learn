package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Address of {@link Host}
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Address {
    @JacksonXmlProperty(localName = "addr", isAttribute = true)
    private String addr;

    @JacksonXmlProperty(localName = "addrtype", isAttribute = true)
    private String addrType;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrType() {
        return addrType;
    }

    public void setAddrType(String addrType) {
        this.addrType = addrType;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
