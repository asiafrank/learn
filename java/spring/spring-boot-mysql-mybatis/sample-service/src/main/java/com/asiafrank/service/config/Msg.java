package com.asiafrank.service.config;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * @author asiafrank created at 30/4/2017.
 */
@Component
public class Msg {

    @Value("${message.name}")
    private String name;

    @Value("${i18n.language}")
    private String language;

    @Value("${i18n.country}")
    private String country;

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    public String PROJECT_NAME;
    public String OK;

    @PostConstruct
    private void init() {
        if (Strings.isNullOrEmpty(language)
                || Strings.isNullOrEmpty(country)) {
            accessor = new MessageSourceAccessor(messageSource);
        } else {
            Locale locale = new Locale(language, country);
            accessor = new MessageSourceAccessor(messageSource, locale);
        }

        PROJECT_NAME = load("PROJECT_NAME");
        OK = load("OK");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String load(String code) {
        return accessor.getMessage(code);
    }
}
