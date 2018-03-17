package com.asiafrank.util.nmap.sample;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * AnotherChild
 * <p>
 * @author zhangxf
 * Created at 12/12/2016.
 */
public class AnotherChild {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String nickname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "AnotherChild{" +
                "name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
