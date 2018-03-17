package com.asiafrank.learn.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zhangxf created at 8/16/2017.
 */
@Component
public class BConfig {

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {
        Resource resource = resourceLoader.getResource("classpath:pp");
        try {
            File f = resource.getFile();
            String path = f.getCanonicalPath();
            System.out.println(path);
            String [] s = f.list();
            if (Objects.nonNull(s)) {
                for (String x : s) {
                    System.out.println(x);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
