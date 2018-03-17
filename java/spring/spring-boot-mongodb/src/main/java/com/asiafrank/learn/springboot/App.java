package com.asiafrank.learn.springboot;

import com.asiafrank.learn.springboot.repo.BaseRepoFactoryBean;
import com.asiafrank.learn.springboot.repo.impl.BaseRepoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * App
 * <p>
 * </p>
 * Created at 1/11/2017.
 *
 * @author zhangxf
 */
@SpringBootApplication
@EnableCaching
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.asiafrank.learn.springboot.repo",
        repositoryBaseClass = BaseRepoImpl.class,
        repositoryFactoryBeanClass = BaseRepoFactoryBean.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
