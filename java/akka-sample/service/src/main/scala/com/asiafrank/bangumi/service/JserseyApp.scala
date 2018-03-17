package com.asiafrank.bangumi.service

import javax.ws.rs.ApplicationPath

import com.asiafrank.bangumi.service.mapper.ObjectMapperContextResolver
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig

/**
  * App
  * <p>
  * </p>
  * Created at 23/1/2017.
  *
  * @author asiafrank
  */
@ApplicationPath("/*")
class JserseyApp extends ResourceConfig {
  packages("com.asiafrank.bangumi.controller")
  register(classOf[ObjectMapperContextResolver])
  register(classOf[JacksonFeature])
}
