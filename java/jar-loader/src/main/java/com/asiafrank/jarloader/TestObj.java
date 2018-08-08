package com.asiafrank.jarloader;

/**
 * @author zhangxf created at 8/7/2018.
 */
public class TestObj {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestObj{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
