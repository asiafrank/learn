package com.asiafrank.learn.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonObjectMapper extends ObjectMapper {
    public JsonObjectMapper() {
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//        setDateFormat(new ISO8601DateFormat());
    }
}