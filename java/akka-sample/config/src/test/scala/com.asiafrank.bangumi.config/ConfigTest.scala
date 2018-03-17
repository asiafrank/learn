package com.asiafrank.bangumi.config

import org.junit.Test
import org.junit.Assert._

/**
  * ConfigTest
  *
  * Created at 2/10/2017.
  *
  * @author asiafrank
  */
class ConfigTest {
  @Test
  def test(): Unit = {
    assertTrue(Config.PG_SERVER == "localhost")
    assertTrue(Config.PG_DATABASE == "bangumi_crawler")
    assertTrue(Config.PG_PORT == 5432)
    assertTrue(Config.PG_USER == "postgres")
    assertTrue(Config.PG_PASSWORD == "postgres")
    assertTrue(Config.URL_ANITAMA_ALL == "http://app.anitama.net/guide/today/all")
    assertTrue(Config.URL_BILIBILI == "")
    assertTrue(Config.URL_TUDOU == "http://zone.tudou.com/zhuifan")
  }
}
