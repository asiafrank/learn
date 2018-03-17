package com.asiafrank.bangumi.config

import java.io.InputStream

import scala.collection.mutable
import scala.io._

/**
  * Config
  *
  * Created at 23/1/2017.
  *
  * @author asiafrank
  */
object Config {

  /**
    * Configuration Properties
    */
  private final val config = loadConf()

  final val PROJECT_VERSION = p("project.version").getOrElse("")

  //==================================================
  // PostgreSQL group
  //==================================================

  final val PG_SERVER = p("pg.server").getOrElse("localhost")

  final val PG_DATABASE = p("pg.database").getOrElse("bangumi_crawler")

  final val PG_PORT = p("pg.port").getOrElse("5432").toInt

  final val PG_USER = p("pg.username").getOrElse("postgres")

  final val PG_PASSWORD = p("pg.password").getOrElse("postgres")
  /**
    * PostgreSQL connection pool init number
    */
  final val PG_CONN_INIT = p("pg.connection.init").getOrElse("4").toInt

  /**
    * PostgreSQL connection pool max number
    */
  final val PG_CONN_MAX = p("pg.connection.max").getOrElse("10").toInt

  //==================================================
  // Bangumi group
  //==================================================

  /**
    * Bangumi ScheduleJob name
    *
    */
  final val BANGUMI = "bangumi"

  final val URL_ANITAMA_ALL = p("url.anitama.all").getOrElse("")

  final val URL_ANITAMA_TODAY = p("url.anitama.today").getOrElse("")

  final val URL_BILIBILI = p("url.bilibili").getOrElse("")

  final val URL_TUDOU = p("url.tudou").getOrElse("")

  //==================================================
  // Data directory group
  //==================================================

  /**
    * File download directory.
    */
  final val DATA_DIR = p("data.dir").getOrElse("./data")

  /**
    * Torrent download directory.
    */
  final val TORRENT_DIR = DATA_DIR + "/torrent"

  /**
    * Picture download directory.
    */
  final val PICTURE_DIR = DATA_DIR + "/picture"

  /**
    * MP3 download directory.
    */
  final val MP3_DIR = DATA_DIR + "/mp3"

  /**
    * Video download directory.
    */
  final val VIDEO_DIR = DATA_DIR + "/video"

  //==================================================
  // Read config file group
  //==================================================

  /**
    * Load key, value from properties file
    *
    * @return k,v mutable.HashMap
    */
  private def loadConf(): mutable.HashMap[String, String] = {
    val map = mutable.HashMap.empty[String, String]
    val stream: InputStream = getClass.getResourceAsStream("/bangumi.properties")
    val lines = Source.fromInputStream(stream).getLines
    lines.foreach(s => {
      val l = s.trim
      if (!l.isEmpty && !l.startsWith("#")) {
        val t = l.split("=")
        if (t.length >= 2) {
          val k = t(0).trim
          val v = t(1).trim
          if (!v.isEmpty)
            map += (k -> v)
        }
      }
    })
    map
  }

  /**
    * Get property value
    *
    * @param k property key
    * @return property value of Option
    */
  private def p(k: String): Option[String] = config.get(k)
}
