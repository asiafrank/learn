package com.asiafrank.microservice.consumer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Locale;


public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        Locale.setDefault(Locale.CHINA);
        return application.sources(ConsumerApplication.class);
    }
}
