package com.asiafrank.bangumi.service.container

import java.io.File

import org.junit.Assert._
import org.junit.Test

/**
  * JacksonContainerTest
  *
  * Created at 2/14/2017.
  *
  * @author zhangxf
  */
class JacksonContainerTest {

  @Test
  def test(): Unit = {
    val mapper = JacksonContainer.mapper
    val classLoader = getClass.getClassLoader
    val jsonFile = new File(classLoader.getResource("anitama-all.json").getFile)
    val root = mapper.readTree(jsonFile)
    val list0 = root.at("/data/guide").get(0)
    val bangumiJson = list0.at("/list").get(0)
    assertEquals(45, bangumiJson.get("bid").asInt())
  }
}
