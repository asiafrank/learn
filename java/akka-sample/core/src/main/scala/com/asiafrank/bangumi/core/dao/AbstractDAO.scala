package com.asiafrank.bangumi.core.dao

import java.sql.ResultSet
import java.time.OffsetDateTime

import com.asiafrank.bangumi.core.Table
import com.asiafrank.bangumi.core.model._
import com.asiafrank.bangumi.core.util.ResourceType._
import com.asiafrank.bangumi.core.util.{ResourceStatus, ResourceType, VideoResolution}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * AbstractDAO
  *
  * Created at 31/1/2017.
  *
  * @author asiafrank
  */
abstract class AbstractDAO[T <: Table] extends DAO[T] {

  /**
    * Table name in database
    */
  protected val tableName: String

  /**
    * Columns string for SQL
    */
  protected val columns: String

  //==================================================
  // Collect data util methods
  //==================================================

  //-----------------------------
  // Schedule Group
  //-----------------------------

  // collect only site part
  protected def collectOnlySite(rs: ResultSet, prefix: String): Option[Site] = {
    val id = rs.getLong(s"${prefix}id")
    if (id != 0L) {
      val s = new Site
      s.id = id
      s.name = rs.getString(s"${prefix}name")
      s.baseUrl = rs.getString(s"${prefix}base_url")
      s.seeds = rs.getArray(s"${prefix}seeds").getArray.asInstanceOf[Array[String]].toList
      s.interests = {
        val arr = rs.getArray(s"${prefix}interests").getArray.asInstanceOf[Array[String]]
        val buffer = new ListBuffer[ResourceType]
        arr.foreach(s => {
          buffer += ResourceType.withName(s)
        })
        buffer.toList
      }
      s.depth = rs.getInt(s"${prefix}depth")
      s.createdAt = rs.getObject(s"${prefix}created_at", classOf[OffsetDateTime])
      s.updatedAt = rs.getObject(s"${prefix}updated_at", classOf[OffsetDateTime])
      Some(s)
    } else None
  }

  //-----------------------------
  // Resource Group
  //-----------------------------

  // collect only video part
  protected def collectOnlyVideo(rs: ResultSet, prefix: String): Option[Video] = {
    val id = rs.getLong(s"${prefix}id")
    if (id != 0L) {
      val v = new Video
      v.id = id
      v.name = rs.getString(s"${prefix}name")
      v.url = rs.getString(s"${prefix}url")
      v.path = rs.getString(s"${prefix}path")
      v.fileSize = rs.getLong(s"${prefix}file_size")
      v.resourceType = ResourceType.withName(rs.getString(s"${prefix}resource_type"))
      v.status = ResourceStatus.withName(rs.getString(s"${prefix}status"))
      v.subtitleGroup = rs.getString(s"${prefix}subtitle_group")
      v.resolution = VideoResolution.withName(rs.getString(s"${prefix}resolution"))
      v.duration = rs.getLong(s"${prefix}duration")
      v.createdAt = rs.getObject(s"${prefix}created_at", classOf[OffsetDateTime])
      v.updatedAt = rs.getObject(s"${prefix}updated_at", classOf[OffsetDateTime])
      Some(v)
    } else None
  }

  // collect only picture part
  def collectOnlyPicture(rs: ResultSet, prefix: String): Option[Picture] = {
    val id = rs.getLong(s"${prefix}id")
    if (id != 0L) {
      val p = new Picture
      p.id = id
      p.name = rs.getString(s"${prefix}name")
      p.url = rs.getString(s"${prefix}url")
      p.path = rs.getString(s"${prefix}path")
      p.fileSize = rs.getLong(s"${prefix}file_size")
      p.resourceType = ResourceType.withName(rs.getString(s"${prefix}resource_type"))
      p.status = ResourceStatus.withName(rs.getString(s"${prefix}status"))
      p.width = rs.getLong(s"${prefix}width")
      p.height = rs.getLong(s"${prefix}height")
      p.createdAt = rs.getObject(s"${prefix}created_at", classOf[OffsetDateTime])
      p.updatedAt = rs.getObject(s"${prefix}updated_at", classOf[OffsetDateTime])
      Some(p)
    } else None
  }

  //-----------------------------
  // Bangumi Group
  //-----------------------------

  protected def collectOnlyLink(rs: ResultSet, prefix: String): Option[Link] = {
    val id = rs.getLong(s"${prefix}id")
    if (id != 0L) {
      val l = new Link
      l.id = id
      l.bangumiId = rs.getLong(s"${prefix}bangumi_id")
      l.playSite = rs.getString(s"${prefix}play_site")
      l.playUrl = rs.getString(s"${prefix}play_url")
      l.createdAt = rs.getObject(s"${prefix}created_at", classOf[OffsetDateTime])
      l.updatedAt = rs.getObject(s"${prefix}updated_at", classOf[OffsetDateTime])
      Some(l)
    } else None
  }

  protected def collectLinkList(rs: ResultSet, prefix: String,
                                head: List[Link],
                                link_map: mutable.HashMap[Long, Link]): List[Link] = {
    val id = rs.getLong(s"${prefix}id")
    val headOpt = Option(head)
    val opt = link_map.get(id)
    val linkOpt = collectOnlyLink(rs, prefix)
    if (opt.isEmpty && linkOpt.isDefined) {
      val l = linkOpt.get
      link_map += (l.id -> l)
      if (headOpt.isEmpty) List(l)
      else head :+ l
    } else {
      head
    }
  }

  protected def collectEpisode(rs: ResultSet, prefix: String,
                               em_map: mutable.HashMap[Long, EpisodeMeta]): Option[Episode] = {
    val id = rs.getLong(s"${prefix}id")
    if (id != 0L) {
      val e = new Episode
      e.id = id
      e.bangumiId = rs.getLong(s"${prefix}bangumi_id")
      e.num = rs.getInt(s"${prefix}num")
      e.video = collectOnlyVideo(rs, s"${prefix}v_").orNull
      e.meta = collectEpisodeMetaList(rs, s"${prefix}em_", e.meta, em_map)
      Some(e)
    } else None
  }

  protected def collectEpisodeList(rs: ResultSet, prefix: String,
                                   head: List[Episode],
                                   e_map: mutable.HashMap[Long, Episode],
                                   em_map: mutable.HashMap[Long, EpisodeMeta]): List[Episode] = {
    val id = rs.getLong(s"${prefix}id")
    val headOpt = Option(head)
    val opt = e_map.get(id)
    val eOpt = collectEpisode(rs, prefix, em_map)
    if (opt.isEmpty && eOpt.isDefined) {
      val e = eOpt.get
      e_map += (e.id -> e)
      if (headOpt.isEmpty) List(e)
      else head :+ e
    } else {
      head
    }
  }

  protected def collectEpisodeMeta(rs: ResultSet, prefix: String): Option[EpisodeMeta] = {
    val id = rs.getLong(s"${prefix}id")
    if (id != 0L) {
      val em = new EpisodeMeta
      em.id = id
      em.episodeId = rs.getLong(s"${prefix}episode_id")
      em.link = collectOnlyLink(rs, s"${prefix}l_").orNull
      em.linkPlayTime = rs.getObject(s"${prefix}link_play_time", classOf[OffsetDateTime])
      em.createdAt = rs.getObject(s"${prefix}created_at", classOf[OffsetDateTime])
      em.updatedAt = rs.getObject(s"${prefix}updated_at", classOf[OffsetDateTime])
      Some(em)
    } else None
  }

  protected def collectEpisodeMetaList(rs: ResultSet, prefix: String,
                                       head: List[EpisodeMeta],
                                       em_map: mutable.HashMap[Long, EpisodeMeta]): List[EpisodeMeta] = {
    val id = rs.getLong(s"${prefix}id")
    val headOpt = Option(head)
    val opt = em_map.get(id)
    val emOpt = collectEpisodeMeta(rs, prefix)
    if (opt.isEmpty && emOpt.isDefined) {
      val em = emOpt.get
      em_map += (em.id -> em)
      if (headOpt.isEmpty) List(em)
      else head :+ em
    } else {
      head
    }
  }
}
