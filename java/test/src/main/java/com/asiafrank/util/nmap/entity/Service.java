package com.asiafrank.util.nmap.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Service of {@link Port}
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
public class Service {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String product;

    @JacksonXmlProperty(isAttribute = true)
    private String version;

    @JacksonXmlProperty(localName = "extrainfo", isAttribute = true)
    private String extraInfo;

    @JacksonXmlProperty(localName = "ostype", isAttribute = true)
    private String osType;

    @JacksonXmlProperty(isAttribute = true)
    private String method;

    /**
     *  The conf attribute measures the confidence Nmap has that the service name is correct.
     *  The values range from one (least confident) to ten. Nmap only has a confidence level
     *  of 3 for ports determined by table lookup, while it is highly confident (level 10)
     *  that port 22 of Example 13.10, “Nmap XML port elements”
     *  ({@code https://nmap.org/book/output-formats-xml-output.html#output-formats-xml-port-elements})
     *  is OpenSSH, because Nmap
     *  connected to the port and found an SSH server identifying as OpenSSH.
     */
    @JacksonXmlProperty(isAttribute = true)
    private int conf;

    @JacksonXmlProperty
    private String cpe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getConf() {
        return conf;
    }

    public void setConf(int conf) {
        this.conf = conf;
    }

    public String getCpe() {
        return cpe;
    }

    public void setCpe(String cpe) {
        this.cpe = cpe;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }
}
