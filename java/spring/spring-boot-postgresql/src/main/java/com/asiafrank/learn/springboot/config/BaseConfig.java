package com.asiafrank.learn.springboot.config;

import com.asiafrank.learn.springboot.bo.SampleBO;
import com.asiafrank.learn.springboot.factory.BOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BaseConfig
 * <p>
 * </p>
 * Created at 1/16/2017.
 *
 * @author zhangxf
 */
@Configuration
public class BaseConfig {
    private SampleBO sampleBO;

    @Bean
    public BOFactory boFactory() {
        BOFactory boFactory = BOFactory.instance();
        boFactory.setSampleBO(sampleBO);
        return boFactory;
    }

    @Autowired
    public void setSampleBO(SampleBO sampleBO) {
        this.sampleBO = sampleBO;
    }
}
