package com.asiafrank.util.nmap.sample;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Dream
 * <p>
 * </p>
 *
 * @author zhangxf
 * Created at 12/12/2016.
 */
public class Dream {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlText
    private String difficulty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Dream{" +
                "name='" + name + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
