package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.LinkedList;

/**
 * OSClass
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class OSClass {
    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String vendor;

    @JacksonXmlProperty(localName = "osfamily", isAttribute = true)
    private String osFamily;

    @JacksonXmlProperty(isAttribute = true)
    private int accuracy;

    @JacksonXmlProperty(localName = "cpe")
    private LinkedList<String> cpes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getOsFamily() {
        return osFamily;
    }

    public void setOsFamily(String osFamily) {
        this.osFamily = osFamily;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public LinkedList<String> getCpes() {
        return cpes;
    }

    public void setCpes(LinkedList<String> cpes) {
        this.cpes = cpes;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
