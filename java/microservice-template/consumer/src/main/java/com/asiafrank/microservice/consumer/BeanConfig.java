package com.asiafrank.microservice.consumer;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangxiaofan 2019/04/20-10:30
 */
@Configuration
public class BeanConfig implements WebMvcConfigurer {

    /**
     * i18n 可以单独一个 jar 模块。message_{}.properties 里的 key 维护在一个 Constant 类里。
     * 供 ControllerAdvice 调用, 返回提示
     */
    @Bean
    public MessageSource messageSource () {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        return messageSource;
    }
}
