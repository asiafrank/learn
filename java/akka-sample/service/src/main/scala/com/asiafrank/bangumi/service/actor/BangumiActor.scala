package com.asiafrank.bangumi.service.actor

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import akka.actor.{Actor, Props}
import com.asiafrank.bangumi.config.Config
import com.asiafrank.bangumi.core.bo.impl.{BangumiBO, LinkBO}
import com.asiafrank.bangumi.core.model.{Bangumi, Link}
import com.asiafrank.bangumi.core.util.ResourceType
import com.asiafrank.bangumi.service.container.{ActorContainer, JacksonContainer}
import com.asiafrank.bangumi.service.util.Status._
import com.asiafrank.bangumi.service.util.{Message, ResourceWrapper}
import com.fasterxml.jackson.databind.JsonNode
import com.typesafe.scalalogging.Logger
import org.jsoup.Jsoup

import scala.collection.JavaConversions._

/**
  * BangumiActor
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class BangumiActor extends Actor {
  val log = Logger(classOf[BangumiActor])

  /*----------------- URLs -----------------*/
  val anitama_all = Config.URL_ANITAMA_ALL
  val anitama_today = Config.URL_ANITAMA_TODAY
  val bilibili = Config.URL_BILIBILI
  val tudou = Config.URL_TUDOU

  /*----------------- Util Fields -----------------*/
  val timeout = 5000
  val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
  val mapper = JacksonContainer.mapper

  /*----------------- BOs -----------------*/
  val bangumiBO = BangumiBO()
  val linkBO = LinkBO()

  /*----------------- actors -----------------*/
  val resourceCrawl = ActorContainer.resourceCrawl

  override def receive: Receive = {
    case Message(INIT, _)  => init()
    case Message(START, _) => start()
    case m                 => unhandled(m)
  }

  def init(): Unit = {
    val response = Jsoup.connect(anitama_all).timeout(timeout).ignoreContentType(true).execute().body()
    anitamaAllAnalyse(response)
  }

  def start(): Unit = {
    if (!anitama_today.isEmpty) {
      val response = Jsoup.connect(anitama_today).timeout(timeout).ignoreContentType(true).execute().body()
      anitamaTodayAnalyse(response)
    }
    // TODO: crawl bilbili and tudou, update higher resolution picture
  }

  //==================================================
  // Data analyse util methods
  //==================================================

  /**
    * Analyse anitama `data`, save to db
    *
    * @param data json response
    */
  private def anitamaAllAnalyse(data: String): Unit = {
    val root = mapper.readTree(data)
    val guide = root.at("/data/guide")
    var list: JsonNode = null
    var playAt: String = null
    for (it <- guide) {
      list = it.get("/list")
      playAt = it.get("playDate").asText()
      for (b <- list) {
        val bangumi = new Bangumi
        bangumi.playAt = LocalDate.parse(playAt, dateFormatter)
        bangumi.name = textOf(b.get("title"))
        bangumi.alias = "-"
        bangumi.originTime = textOf(b.get("originTime"))
        bangumi.originStation = textOf(b.get("originStation"))
        bangumiBO.insert(bangumi)

        val link = new Link
        link.bangumiId = bangumi.id
        link.playUrl = textOf(b.get("playUrl"))
        link.playSite = siteOf(b.get("playSite"), link.playUrl)
        linkBO.insert(link)

        // for update Bangumi cover
        val coverUrl = textOf(b.get("cover"))
        val rw = ResourceWrapper(bangumi.id, ResourceType.PICTURE, coverUrl)
        resourceCrawl ! rw
      }
    }
  }

  /**
    * Analyse anitama `data` of today, compare db and update
    *
    * @param data json response
    */
  private def anitamaTodayAnalyse(data: String): Unit = {
    val today = LocalDate.now()
    val todayMap = bangumiBO.findTodayMap()
    val root = mapper.readTree(data)
    val list = root.at("/data/list")
    var opt: Option[Bangumi] = null
    var temp: Bangumi = null
    for (b <- list) {
      val title = textOf(b.get("title"))
      opt = todayMap.get(title)
      val expectedB = new Bangumi
      expectedB.originTime = textOf(b.get("originTime"))
      expectedB.originStation = textOf(b.get("originStation"))
      val expectedCoverUrl = textOf(b.get("cover"))
      val expectedL = new Link
      expectedL.playUrl = textOf(b.get("playUrl"))
      expectedL.playSite = siteOf(b.get("playSite"), expectedL.playUrl)

      if (opt.isDefined) {
        temp = opt.get
        compareAndUpdateBangumi(temp, expectedB)
        compareAndUpdateBangumiCover(temp, expectedCoverUrl)
        compareAndUpdateLink(temp.id, temp.links, expectedL)
      } else {
        expectedB.playAt = today
        expectedB.name = title
        expectedB.alias = "-"
        expectedB.originTime = textOf(b.get("originTime"))
        expectedB.originStation = textOf(b.get("originStation"))
        bangumiBO.insert(expectedB)

        expectedL.bangumiId = expectedB.id
        linkBO.insert(expectedL)

        // for update Bangumi cover
        val rw = ResourceWrapper(expectedB.id, ResourceType.PICTURE, expectedCoverUrl)
        resourceCrawl ! rw
      }
    }
  }

  /**
    * Only compare `originTime` and `originStation`
    *
    * @param origin          the origin Bangumi from db
    * @param expectedBangumi the expected Bangumi object
    */
  def compareAndUpdateBangumi(origin: Bangumi, expectedBangumi: Bangumi): Unit = {
    val oot = origin.originTime
    val eot = expectedBangumi.originTime
    val oos = origin.originStation
    val eos = expectedBangumi.originStation
    if (!oot.equals(eot) || !oos.equals(eos)) {
      val entity = origin
      entity.originTime = eot
      entity.originStation = eos
      bangumiBO.update(entity)
    }
  }

  /**
    * Only compare `cover.url`
    *
    * @param origin           the origin Bangumi from db
    * @param expectedCoverUrl the expected Bangumi cover url
    */
  def compareAndUpdateBangumiCover(origin: Bangumi, expectedCoverUrl: String): Unit = {
    if (!expectedCoverUrl.isEmpty) {
      val oCoverUrl = origin.cover.url
      if (!oCoverUrl.equals(expectedCoverUrl)) {
        // for update Bangumi cover
        val rw = ResourceWrapper(origin.id, ResourceType.PICTURE, expectedCoverUrl)
        resourceCrawl ! rw
      }
    }
  }

  /**
    * Find same `playSite` in `links`, compare `playUrl` and update.
    *
    * @param bangumiId bangumiId
    * @param links     links from bangumi
    * @param expectedL the expected link object
    */
  def compareAndUpdateLink(bangumiId: Long, links: List[Link], expectedL: Link): Unit = {
    val opt = Option(links)
    if (opt.isDefined) {
      var oPlaySite: String = null
      var oPlayUrl: String = null
      val ePlaySite = expectedL.playSite
      val ePlayUrl = expectedL.playUrl
      for (l <- opt.get) {
        oPlaySite = l.playSite
        oPlayUrl = l.playUrl
        if (oPlaySite.equals(ePlaySite)) {
          if (!oPlayUrl.equals(ePlayUrl)) {
            l.playUrl = ePlayUrl
            linkBO.update(l)
          }
        }
      }
    } else {
      expectedL.bangumiId = bangumiId
      linkBO.insert(expectedL)
    }
  }

  /**
    * Check if JsonNode is null, return empty string
    * else `asText()`
    *
    * @param o JsonNode maybe null
    * @return
    */
  private def textOf(o: JsonNode): String = {
    if (o == null) ""
    else o.asText()
  }

  private def siteOf(siteNode: JsonNode, url: String): String = {
    val s = textOf(siteNode)
    if (!s.isEmpty) {
      if (url.contains("bilibili")) {
        "哔哩哔哩"
      } else if (url.contains("tudou")) {
        "土豆"
      } else if (url.contains("iqiyi")) {
        "爱奇艺"
      } else if (url.contains("qq.com")) {
        "腾讯视频"
      } else if (url.contains("dilidili")) {
        "嘀哩嘀哩"
      } else {
        "-"
      }
    } else s
  }
}

object BangumiActor {
  def props = Props(new BangumiActor)
}
