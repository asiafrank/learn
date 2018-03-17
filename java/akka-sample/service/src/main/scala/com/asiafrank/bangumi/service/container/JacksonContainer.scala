package com.asiafrank.bangumi.service.container

import com.fasterxml.jackson.databind.ObjectMapper

/**
  * JacksonContainer
  * <p>
  * </p>
  * Created at 2/14/2017.
  *
  * @author zhangxf
  */
object JacksonContainer {
  lazy val mapper = new ObjectMapper()
}
