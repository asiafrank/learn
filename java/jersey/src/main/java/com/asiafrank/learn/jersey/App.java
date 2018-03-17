package com.asiafrank.learn.jersey;

import com.asiafrank.learn.jersey.mapper.ObjectMapperContextResolver;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Application
 * <p>
 * </p>
 * Created at 23/1/2017.
 *
 * @author asiafrank
 */
@ApplicationPath("/")
public class App extends ResourceConfig {
    public App() {
        packages("com.asiafrank.learn.jersey.controller");
        register(ObjectMapperContextResolver.class);
//        register(JacksonFeature.class);
    }
}
