package com.asiafrank.learn.springboot.config;

import com.asiafrank.learn.springboot.core.bo.SampleBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhangxf created at 9/7/2017.
 */
@Component("bb")
@Scope("prototype")
public class BB {

    private final String name;

    @Autowired
    private SampleBO sampleBO;

    private int a;

    public BB(String name) {
        this.name = name;
    }

    @PostConstruct
    public void init() {
        System.out.println("bb init" + name);
    }

    public String getName() {
        return name;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "BB{" +
               "name='" + name + '\'' +
               ", sampleBO=" + sampleBO +
               ", a=" + a +
               '}';
    }
}
