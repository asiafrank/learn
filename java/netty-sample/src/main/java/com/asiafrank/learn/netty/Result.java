package com.asiafrank.learn.netty;

/**
 * Script Result
 * @author zhangxf created at 5/3/2018.
 */
public class Result {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
               "name='" + name + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}
