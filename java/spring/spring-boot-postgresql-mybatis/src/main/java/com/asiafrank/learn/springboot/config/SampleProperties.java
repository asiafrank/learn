package com.asiafrank.learn.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SampleProperties
 * <p>
 * </p>
 * Created at 4/11/2017.
 *
 * @author zhangxf
 */
@ConfigurationProperties(prefix = "sample")
public class SampleProperties {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
