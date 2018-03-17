package com.asiafrank.bangumi.service.actor

import java.io.{File, FileNotFoundException, FileOutputStream, IOException}
import java.util.UUID
import javax.imageio.ImageIO

import akka.actor.{Actor, Props}
import com.asiafrank.bangumi.config.Config
import com.asiafrank.bangumi.core.bo.impl.{BangumiBO, PictureBO}
import com.asiafrank.bangumi.core.model.Picture
import com.asiafrank.bangumi.core.util.ResourceStatus._
import com.asiafrank.bangumi.core.util.ResourceType._
import com.asiafrank.bangumi.core.util.URLs
import com.asiafrank.bangumi.service.util.ResourceWrapper
import com.typesafe.scalalogging.Logger
import org.jsoup.Jsoup

/**
  * ResourceActor
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class ResourceCrawlActor extends Actor {
  val log = Logger(classOf[ResourceCrawlActor])

  /*----------------- DIRs -----------------*/
  val picture_dir = mkdirOf(Config.PICTURE_DIR)
  val torrent_dir = mkdirOf(Config.TORRENT_DIR)
  val mp3_dir = mkdirOf(Config.MP3_DIR)
  val video_dir = mkdirOf(Config.VIDEO_DIR)

  /*----------------- BOs -----------------*/
  val bangumiBO = BangumiBO()
  val pictureBO = PictureBO()

  override def receive: Receive = {
    case rw: ResourceWrapper => crawl(rw)
    case m                   => unhandled(m)
  }

  /**
    * Start crawl Picture, MP3, Torrent...
    *
    * @param rw ResourceWrapper
    */
  private def crawl(rw: ResourceWrapper): Unit = {
    val bangumiId = rw.bangumiId
    val resourceType = rw.resourceType
    val url = rw.url
    if (bangumiId != 0 && URLs.verify(url)) {
      resourceType match {
        case PICTURE => doPicture(bangumiId, url)
      }
    }
  }

  /**
    * Download picture, then save to db, and bind to bangumi
    *
    * @param bangumiId bangumiId
    * @param url       download url
    */
  private def doPicture(bangumiId: Long, url: String): Unit = {
    val resOpt = {
      try {
        val res = Jsoup.connect(url).timeout(1000).ignoreContentType(true).execute()
        Some(res)
      } catch {
        case e: IOException => None
      }
    }
    if (resOpt.isEmpty) {return}

    val res = resOpt.get
    val contextType = res.contentType()
    val suffix = {
      contextType match {
        case "image/gif"  => ".gif"
        case "image/jpeg" => ".jpg"
        case "image/png"  => ".png"
        case _            => ".unknown"
      }
    }

    if (!suffix.equals(".unknown")) {
      val name = UUID.randomUUID().toString
      val file = new File(picture_dir + File.separator + name + suffix)
      var out: FileOutputStream = null
      try {
        out = new FileOutputStream(file)
      } catch {
        case e: FileNotFoundException => log.error("File not found")
      } finally {
        if (out != null) {
          try {
            out.close()
          } catch {
            case e: IOException => log.error("", e)
          }
        }
      }

      val bOpt = bangumiBO.findOne(bangumiId)
      if (bOpt.isDefined) {
        val bangumi = bOpt.get
        val p = new Picture
        p.name = "cover-" + bangumi.name
        p.url = url
        p.path = file.getAbsolutePath
        p.fileSize = file.length()
        p.resourceType = PICTURE
        p.status = DONE
        try {
          val image = ImageIO.read(file)
          p.width = image.getWidth
          p.height = image.getHeight
        } catch {
          case e: IOException => log.error("", e)
        }
        pictureBO.insert(p)
        bangumi.cover = p
        bangumiBO.update(bangumi)
      }
    }
  }

  /**
    * If `dir` not exists, create.
    *
    * @param dir directory path
    * @return valid directory path
    */
  private def mkdirOf(dir: String): String = {
    val opt = Option(dir)
    if (opt.isDefined && dir.length > 0) {
      val d = new File(dir)
      if (!d.exists()) {
        d.mkdirs()
      }
      dir
    } else ""
  }
}

object ResourceCrawlActor {
  def props = Props(new ResourceCrawlActor)
}
