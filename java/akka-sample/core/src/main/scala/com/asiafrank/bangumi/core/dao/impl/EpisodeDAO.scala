package com.asiafrank.bangumi.core.dao.impl

import java.sql.{Connection, ResultSet, SQLException, Types}
import java.time.OffsetDateTime

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.dao.AbstractDAO
import com.asiafrank.bangumi.core.model.{Episode, EpisodeMeta}
import com.typesafe.scalalogging.Logger

import scala.collection.mutable

/**
  * EpisodeDAO
  *
  * Created at 2/13/2017.
  *
  * @author zhangxf
  */
class EpisodeDAO extends AbstractDAO[Episode] {
  val log = Logger(classOf[EpisodeDAO])
  override protected val tableName: String = "\"episode\" AS e"
  override protected val columns: String = "e.id,e.bangumi_id,e.num,e.video_id,e.created_at,e.updated_at"

  // LEFT JOIN $videoTableName ON v.id = e.video_id
  private val videoTableName: String = "\"video\" AS v"
  private val videoColumns: String = {
    """v.id              AS v_id
      |,v.name           AS v_name
      |,v.url            AS v_url
      |,v.path           AS v_path
      |,v.file_size      AS v_file_size
      |,v.resource_type  AS v_resource_type
      |,v.status         AS v_status
      |,v.site_id        AS v_site_id
      |,v.subtitle_group AS v_subtitle_group
      |,v.resolution     AS v_resolution
      |,v.duration       AS v_duration
      |,v.created_at     AS v_created_at
      |,v.updated_at     AS v_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $videoSiteTableName ON v_s.id = v.site_id
  private val videoSiteTableName: String = "\"site\" AS v_s"
  private val videoSiteColumns: String = {
    """v_s.id              AS v_s_id
      |,v_s.name           AS v_s_name
      |,v_s.base_url       AS v_s_base_url
      |,v_s.seeds          AS v_s_seeds
      |,v_s.interests      AS v_s_interests
      |,v_s.depth          AS v_s_depth
      |,v_s.created_at     AS v_s_created_at
      |,v_s.updated_at     AS v_s_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $eMetaTableName ON em.episode_id = e.id
  private val eMetaTableName: String = "\"episode_meta\" AS em"
  private val eMetaColumns: String = {
    """em.id                AS em_id
      |,em.episode_id       AS em_episode_id
      |,em.link_id          AS em_link_id
      |,em.link_play_time   AS em_link_play_time
      |,em.created_at       AS em_created_at
      |,em.updated_at       AS em_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $eMetaLinkTableName ON em_link.id = em.link_id
  private val eMetaLinkTableName: String = "\"link\" AS em_link"
  private val eMetaLinkColumns: String = {
    """em_link.id          AS em_link_id
      |,em_link.play_site  AS em_link_play_site
      |,em_link.play_url   AS em_link_play_url
      |,em_link.created_at AS em_link_created_at
      |,em_link.updated_at AS em_link_updated_at""".stripMargin.replace('\n', ' ')
  }

  /**
    * Collect data from [[ResultSet]] to new instance of [[Episode]]
    *
    * @param rs ResultSet
    * @return instance of [[Episode]]
    */
  private def collectData(rs: ResultSet, map: mutable.HashMap[Long, Episode]): Unit = {
    val em_map = new mutable.HashMap[Long, EpisodeMeta] // Episode map
    while (rs.next()) {
      val id = rs.getLong("id")
      val opt = map.get(id)
      if (opt.isEmpty) {
        // map not contain
        val row = new Episode
        row.id = id
        row.bangumiId = rs.getLong("bangumi_id")
        row.num = rs.getInt("num")
        row.video = collectOnlyVideo(rs, "v_").orNull
        row.meta = collectEpisodeMetaList(rs, "em_", row.meta, em_map)
        map += (id -> row)
      } else {
        // map contain
        val row = opt.get
        row.meta = collectEpisodeMetaList(rs, "em_", row.meta, em_map)
      }
    }
  }

  val baseSQL = s"SELECT $columns,$videoColumns,$videoSiteColumns,$eMetaColumns,$eMetaLinkColumns " +
    s"FROM $tableName " +
    s"LEFT JOIN $videoTableName ON v.id = e.video_id " +
    s"LEFT JOIN $videoSiteTableName ON v_s.id = v.site_id " +
    s"LEFT JOIN $eMetaTableName ON em.episode_id = e.id " +
    s"LEFT JOIN $eMetaLinkTableName ON em_link.id = em.link_id"

  /**
    * Find all rows.
    *
    * @param conn sql connection
    * @return [[Page]] of [[Episode]]
    */
  override def findAll(conn: Connection): Page[Episode] = {
    val sql = baseSQL
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    val rs = st.executeQuery()
    val map = new mutable.HashMap[Long, Episode]()
    collectData(rs, map)

    rs.close()
    st.close()
    val elements = map.values.toList
    Page[Episode](elements, 1, elements.size, elements.size)
  }

  /**
    * Find by id.
    *
    * @param id   value of `id` column of the row that expected to find
    * @param conn sql connection
    * @return [[Option]] of [[Episode]]
    */
  override def findOne(id: Long, conn: Connection): Option[Episode] = {
    val sql = s"$baseSQL WHERE e.id = ?"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    st.setLong(1, id)

    val rs = st.executeQuery()
    val map = new mutable.HashMap[Long, Episode]()
    collectData(rs, map)

    rs.close()
    st.close()
    map.get(id)
  }

  /**
    * Insert entity. `entity` id will be ignored if it is not `null`.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return inserted entity
    */
  override def insert(entity: Episode, conn: Connection): Episode = {
    val columnsNoID = "bangumi_id,num,video_id,created_at,updated_at"
    val sql = s"INSERT INTO $tableName ($columnsNoID) VALUES (?,?,?,now(),now()) RETURNING id,created_at,updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    val vOpt = Option(entity.video)

    val optBangumiId = Option(entity.bangumiId)
    if (optBangumiId.isDefined && optBangumiId.get != 0L)
      st.setLong(1, optBangumiId.get)
    else st.setNull(1, Types.BIGINT)
    st.setInt(2, entity.num)
    if (vOpt.isDefined) st.setLong(3, vOpt.get.id)
    else st.setNull(3, Types.BIGINT)

    val rs = st.executeQuery()
    if (rs.next()) {
      entity.id = rs.getInt("id")
      entity.createdAt = rs.getObject("created_at", classOf[OffsetDateTime])
      entity.updatedAt = rs.getObject("updated_at", classOf[OffsetDateTime])
    } else throw new SQLException("Insert failed, no rows affected.")

    rs.close()
    st.close()
    entity
  }

  /**
    * Update entity. Update all fields, whatever fields are `null`, empty or defined.
    * You'd better invoke [[findOne()]] first, then modify the `entity`,
    * finally invoke this method to make sure the entity fields are all valid.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return updated entity
    */
  override def update(entity: Episode, conn: Connection): Episode = {
    val idOpt = Option(entity.id)
    if (idOpt.isEmpty || idOpt.get == 0L) {
      throw new SQLException("ID should not be 0")
    }

    val updateColumns = "bangumi_id = ?, num = ?, video_id = ?, updated_at = now()"
    val sql = s"UPDATE $tableName SET $updateColumns RETURNING updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    val vOpt = Option(entity.video)

    st.setLong(1, entity.bangumiId)
    st.setInt(2, entity.num)
    if (vOpt.isDefined) st.setLong(3, vOpt.get.id)
    else st.setNull(3, Types.BIGINT)

    val rs = st.executeQuery()
    if (rs.next()) {
      entity.updatedAt = rs.getObject("updated_at", classOf[OffsetDateTime])
    } else throw new SQLException("Update failed, no rows affected.")

    rs.close()
    st.close()
    entity
  }

  /**
    * Delete by id.
    *
    * @param id   value of `id` column of the row that expected to delete
    * @param conn sql connection
    * @return affected rows number.
    *         In this case, return `0` means failed, return `1` means success
    */
  override def delete(id: Long, conn: Connection): Int = {
    val sql = s"DELETE FROM $tableName WHERE id = ?"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    st.setLong(1, id)

    st.executeUpdate()
  }

  /**
    * Delete all rows in database.
    *
    * @param conn sql connection
    * @return affected rows number
    */
  override def deleteAll(conn: Connection): Int = {
    val sql = s"DELETE FROM $tableName"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    st.executeUpdate()
  }
}

object EpisodeDAO {
  private val dao = new EpisodeDAO()

  def apply(): EpisodeDAO = dao
}
