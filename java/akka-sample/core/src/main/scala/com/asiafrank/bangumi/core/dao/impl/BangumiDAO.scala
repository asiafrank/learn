package com.asiafrank.bangumi.core.dao.impl

import java.sql.{Connection, ResultSet, SQLException, Types}
import java.time.{LocalDate, OffsetDateTime}

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.dao.AbstractDAO
import com.asiafrank.bangumi.core.model._
import com.typesafe.scalalogging.Logger

import scala.collection.mutable

/**
  * BangumiDAO
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class BangumiDAO extends AbstractDAO[Bangumi] {
  val log = Logger(classOf[BangumiDAO])
  override protected val tableName: String = "\"bangumi\" AS b"
  override protected val columns: String = "b.id,b.name,b.alias,b.origin_time,b.origin_station,b.cover_id," +
    "b.latest_episode_id,b.finished,b.play_at,b.created_at,b.updated_at"

  // LEFT JOIN $coverTableName ON p.id = b.cover_id
  private val coverTableName: String = "\"picture\" AS p"
  private val coverColumns: String = {
    """p.id              AS p_id
      |,p.name           AS p_name
      |,p.url            AS p_url
      |,p.path           AS p_path
      |,p.file_size      AS p_file_size
      |,p.resource_type  AS p_resource_type
      |,p.status         AS p_status
      |,p.width          AS p_width
      |,p.height         AS p_height
      |,p.created_at     AS p_created_at
      |,p.updated_at     AS p_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $latestEpisodeTableName ON le.bangumi_id = b.id
  private val latestEpisodeTableName: String = "\"episode\" AS le"
  private val latestEpisodeColumns: String = {
    """le.id              AS le_id
      |,le.bangumi_id     AS le_bangumi_id
      |,le.num            AS le_num
      |,le.video_id       AS le_video_id
      |,le.created_at     AS le_created_at
      |,le.updated_at     AS le_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $leVideoTableName ON lev.id = le.video_id
  private val leVideoTableName: String = "\"video\" AS lev"
  private val leVideoColumns: String = {
    """lev.id              AS lev_id
      |,lev.name           AS lev_name
      |,lev.url            AS lev_url
      |,lev.path           AS lev_path
      |,lev.file_size      AS lev_file_size
      |,lev.resource_type  AS lev_resource_type
      |,lev.status         AS lev_status
      |,lev.subtitle_group AS lev_subtitle_group
      |,lev.resolution     AS lev_resolution
      |,lev.duration       AS lev_duration
      |,lev.created_at     AS lev_created_at
      |,lev.updated_at     AS lev_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $leMetaTableName ON lem.episode_id = le.id
  private val leMetaTableName: String = "\"episode_meta\" AS lem"
  private val leMetaColumns: String = {
    """lem.id                AS lem_id
      |,lem.episode_id       AS lem_episode_id
      |,lem.link_id          AS lem_link_id
      |,lem.link_play_time   AS lem_link_play_time
      |,lem.created_at       AS lem_created_at
      |,lem.updated_at       AS lem_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $leMetaLinkTableName ON lem_link.id = lem.link_id
  private val leMetaLinkTableName: String = "\"link\" AS lem_l"
  private val leMetaLinkColumns: String = {
    """lem_l.id          AS lem_l_id
      |,lem_l.play_site  AS lem_l_play_site
      |,lem_l.play_url   AS lem_l_play_url
      |,lem_l.created_at AS lem_l_created_at
      |,lem_l.updated_at AS lem_l_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $episodeTableName ON le.bangumi_id = b.id
  private val episodeTableName: String = "\"episode\" AS e"
  private val episodeColumns: String = {
    """e.id              AS e_id
      |,e.bangumi_id     AS e_bangumi_id
      |,e.num            AS e_num
      |,e.video_id       AS e_video_id
      |,e.created_at     AS e_created_at
      |,e.updated_at     AS e_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $videoTableName ON ev.id = e.video_id
  private val videoTableName: String = "\"video\" AS ev"
  private val videoColumns: String = {
    """ev.id              AS ev_id
      |,ev.name           AS ev_name
      |,ev.url            AS ev_url
      |,ev.path           AS ev_path
      |,ev.file_size      AS ev_file_size
      |,ev.resource_type  AS ev_resource_type
      |,ev.status         AS ev_status
      |,ev.subtitle_group AS ev_subtitle_group
      |,ev.resolution     AS ev_resolution
      |,ev.duration       AS ev_duration
      |,ev.created_at     AS ev_created_at
      |,ev.updated_at     AS ev_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $eMetaTableName AS em ON em.episode_id = e.id
  private val eMetaTableName: String = "\"episode_meta\" AS em"
  private val eMetaColumns: String = {
    """em.id                AS em_id
      |,em.episode_id       AS em_episode_id
      |,em.link_id          AS em_link_id
      |,em.link_play_time   AS em_link_play_time
      |,em.created_at       AS em_created_at
      |,em.updated_at       AS em_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $eMetaLinkTableName AS em_link ON em_link.id = em.link_id
  private val eMetaLinkTableName: String = "\"link\" AS em_l"
  private val eMetaLinkColumns: String = {
    """em_l.id          AS em_l_id
      |,em_l.play_site  AS em_l_play_site
      |,em_l.play_url   AS em_l_play_url
      |,em_l.created_at AS em_l_created_at
      |,em_l.updated_at AS em_l_updated_at""".stripMargin.replace('\n', ' ')
  }

  // LEFT JOIN $linkTableName AS l ON l.bangumi_id = b.id
  private val linkTableName: String = "\"link\" AS l"
  private val linkColumns: String = {
    """l.id                AS l_id
      |,l.play_site        AS l_play_site
      |,l.play_url         AS l_play_url
      |,l.created_at       AS l_created_at
      |,l.updated_at       AS l_updated_at""".stripMargin.replace('\n', ' ')
  }

  val baseSQL = s"SELECT $columns,$coverColumns,$latestEpisodeColumns,$leVideoColumns,$leMetaColumns,$leMetaLinkColumns," +
    s"$episodeColumns,$videoColumns,$eMetaColumns,$eMetaLinkColumns,$linkColumns " +
    s"FROM $tableName " +
    s"LEFT JOIN $coverTableName         ON p.id = b.cover_id " +
    s"LEFT JOIN $latestEpisodeTableName ON le.bangumi_id = b.id " +
    s"LEFT JOIN $leVideoTableName       ON lev.id = le.video_id " +
    s"LEFT JOIN $leMetaTableName        ON lem.episode_id = le.id " +
    s"LEFT JOIN $leMetaLinkTableName    ON lem_l.id = lem.link_id " +
    s"LEFT JOIN $episodeTableName       ON le.bangumi_id = b.id " +
    s"LEFT JOIN $videoTableName         ON ev.id = e.video_id " +
    s"LEFT JOIN $eMetaTableName         ON em.episode_id = e.id " +
    s"LEFT JOIN $eMetaLinkTableName     ON em_l.id = em.link_id " +
    s"LEFT JOIN $linkTableName          ON l.bangumi_id = b.id"

  /**
    * Collect data from [[ResultSet]] to `map`
    *
    * @param rs ResultSet
    */
  protected def collectData(rs: ResultSet, map: mutable.HashMap[Long, Bangumi]): Unit = {
    val e_map = new mutable.HashMap[Long, Episode]      // Episode map
    val em_map = new mutable.HashMap[Long, EpisodeMeta] // EpisodeMeta map
    val link_map = new mutable.HashMap[Long, Link]      // Link map
    while (rs.next()) {
      val id = rs.getLong("id")
      val opt = map.get(id)
      if (opt.isEmpty) {
        // map not contain
        val row = new Bangumi
        row.id = id
        row.name = rs.getString("name")
        row.alias = rs.getString("alias")
        row.originTime = rs.getString("origin_time")
        row.originStation = rs.getString("origin_station")
        row.cover = collectOnlyPicture(rs, "p_").orNull
        row.latestEpisode = collectEpisode(rs, "le_", em_map).orNull
        row.episodes = collectEpisodeList(rs, "e_", row.episodes, e_map, em_map)
        row.links = collectLinkList(rs, "l_", row.links, link_map)
        row.finished = rs.getBoolean("finished")
        row.playAt = rs.getObject("play_at", classOf[LocalDate])
        row.createdAt = rs.getObject("created_at", classOf[OffsetDateTime])
        row.updatedAt = rs.getObject("updated_at", classOf[OffsetDateTime])
        map += (id -> row)
      } else {
        // map contain
        val row = opt.get
        row.episodes = collectEpisodeList(rs, "e_", row.episodes, e_map, em_map)
        row.links = collectLinkList(rs, "l_", row.links, link_map)
      }
    }
  }

  /**
    * Find all rows.
    *
    * @param conn sql connection
    * @return [[Page]] of [[Bangumi]]
    */
  @throws(classOf[SQLException])
  override def findAll(conn: Connection): Page[Bangumi] = {
    val sql = baseSQL
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    val rs = st.executeQuery()
    val map = new mutable.HashMap[Long, Bangumi]()
    collectData(rs, map)

    rs.close()
    st.close()
    val elements = map.values.toList
    Page[Bangumi](elements, 1, elements.size, elements.size)
  }

  /**
    * Find all rows filter by finished.
    *
    * @param conn sql connection
    * @return [[Page]] of [[Bangumi]]
    */
  @throws(classOf[SQLException])
  def findAll(conn: Connection, finished: Boolean): Page[Bangumi] = {
    val sql = s"$baseSQL where b.finished = ?"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    st.setBoolean(1, finished)

    val rs = st.executeQuery()
    val map = new mutable.HashMap[Long, Bangumi]()
    collectData(rs, map)

    rs.close()
    st.close()
    val elements = map.values.toList
    Page[Bangumi](elements, 1, elements.size, elements.size)
  }

  /**
    * Find by id.
    *
    * @param id   value of `id` column of the row that expected to find
    * @param conn sql connection
    * @return [[Option]] of [[Bangumi]]
    */
  @throws(classOf[SQLException])
  override def findOne(id: Long, conn: Connection): Option[Bangumi] = {
    val sql = s"$baseSQL WHERE b.id = ?"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    st.setLong(1, id)

    val rs = st.executeQuery()
    val map = new mutable.HashMap[Long, Bangumi]()
    collectData(rs, map)

    rs.close()
    st.close()
    map.get(id)
  }

  /**
    * Insert element. `entity` id will be ignored if it is not `null`.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return inserted entity
    */
  @throws(classOf[SQLException])
  override def insert(entity: Bangumi, conn: Connection): Bangumi = {
    val columnsNoID = "name,alias,origin_time,origin_station,cover_id,latest_episode_id,finished,play_at,created_at,updated_at"
    val sql = s"INSERT INTO $tableName ($columnsNoID) VALUES (?,?,?,?,?,?,?,?,now(),now()) RETURNING id,created_at,updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    val leOpt = Option(entity.latestEpisode)
    val cOpt = Option(entity.cover)

    st.setString(1, entity.name)
    st.setString(2, entity.alias)
    st.setString(3, entity.originTime)
    st.setString(4, entity.originStation)
    if (cOpt.isDefined) st.setLong(5, cOpt.get.id)
    else st.setNull(5, Types.BIGINT)
    if (leOpt.isDefined) st.setLong(6, leOpt.get.id)
    else st.setNull(6, Types.BIGINT)
    st.setBoolean(7, entity.finished)
    st.setObject(8, entity.playAt)

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
    * Update element. Update all fields, whatever fields are `null`, empty or defined.
    * You'd better invoke [[findOne()]] first, then modify the `entity`,
    * finally invoke this method to make sure the entity fields are all valid.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return updated entity
    */
  @throws(classOf[SQLException])
  override def update(entity: Bangumi, conn: Connection): Bangumi = {
    val idOpt = Option(entity.id)
    if (idOpt.isEmpty || idOpt.get == 0L) {
      throw new SQLException("ID should not be 0")
    }

    // no created_at
    val updateColumns = "name = ?, alias = ?, origin_time = ? origin_station = ?, " +
      "cover_id = ?, latest_episode_id = ?, finished = ?, play_at = ?, updated_at = now()"
    val sql = s"UPDATE $tableName SET $updateColumns WHERE id = ? RETURNING updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    val leOpt = Option(entity.getLatestEpisode)
    val cOpt = Option(entity.cover)

    st.setString(1, entity.name)
    st.setString(2, entity.alias)
    st.setString(3, entity.originTime)
    st.setString(4, entity.originStation)
    if (cOpt.isDefined) st.setLong(5, cOpt.get.id)
    else st.setNull(5, Types.BIGINT)
    if (leOpt.isDefined) st.setLong(6, leOpt.get.id)
    else st.setNull(6, Types.BIGINT)
    st.setBoolean(7, entity.finished)
    st.setObject(8, entity.playAt)

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
  @throws(classOf[SQLException])
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
  @throws(classOf[SQLException])
  override def deleteAll(conn: Connection): Int = {
    val sql = s"DELETE FROM $tableName"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    st.executeUpdate()
  }

  /**
    * Find Today bangumi list for analyse.
    *
    * @return Map, key: title, value: Bangumi
    */
  def findByDay(conn: Connection, day: LocalDate): Page[Bangumi] = {
    val optDay = Option(day)
    if (optDay.isEmpty) {
      throw new IllegalArgumentException
    }

    val sql = s"$baseSQL WHERE play_at = ?"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    st.setObject(1, day)

    val rs = st.executeQuery()
    val map = new mutable.HashMap[Long, Bangumi]()
    collectData(rs, map)

    rs.close()
    st.close()
    val elements = map.values.toList
    Page[Bangumi](elements, 1, elements.size, elements.size)
  }

  /*----------------- find without `JOIN` only bangumi part -----------------*/
}

object BangumiDAO {
  private val dao = new BangumiDAO()
  def apply(): BangumiDAO = dao
}
