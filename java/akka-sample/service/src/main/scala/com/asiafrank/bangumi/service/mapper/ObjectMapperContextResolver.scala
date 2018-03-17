package com.asiafrank.bangumi.service.mapper

import java.text.SimpleDateFormat
import javax.ws.rs.ext.{ContextResolver, Provider}

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind._
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

/**
  * ObjectMapperContextResolver
  *
  * Created at 25/1/2017.
  *
  * @author asiafrank
  */
@Provider
class ObjectMapperContextResolver extends ContextResolver[ObjectMapper]{
  private val mapper = {
    val m = new ObjectMapper()
    m.registerModule(new JavaTimeModule)
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
      .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
      .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
      .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"))
    m
  }

  override def getContext(t: Class[_]): ObjectMapper = mapper
}
