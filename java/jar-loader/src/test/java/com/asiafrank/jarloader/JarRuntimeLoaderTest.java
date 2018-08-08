package com.asiafrank.jarloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zhangxf created at 8/7/2018.
 */
public class JarRuntimeLoaderTest {

    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TestObj obj = mapper.readValue("{\"id\": 10, \"name\": \"test obj\"}", TestObj.class);

        Assert.assertEquals(obj.getId(), 10);
        Assert.assertEquals(obj.getName(), "test obj");
    }
}
