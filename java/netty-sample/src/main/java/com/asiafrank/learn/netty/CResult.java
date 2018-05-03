package com.asiafrank.learn.netty;

import com.sun.jna.Structure;
import com.sun.jna.WString;

import java.util.List;

/**
 * Script Result for C
 * @author zhangxf created at 5/3/2018.
 */
public class CResult extends Structure {
    public static class ByValue extends CResult implements Structure.ByValue { }

    public WString name;
    public WString description;

    @Override
    protected List<String> getFieldOrder() {
        return createFieldsOrder("name", "description");
    }

    @Override
    public String toString() {
        return "{" +
               "name='" + name + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}
