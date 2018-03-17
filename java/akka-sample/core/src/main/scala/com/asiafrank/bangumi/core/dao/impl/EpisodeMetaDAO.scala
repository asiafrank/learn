package com.asiafrank.bangumi.core.dao.impl

import java.sql.{Connection, ResultSet, SQLException, Types}
import java.time.OffsetDateTime

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.dao.AbstractDAO
import com.asiafrank.bangumi.core.model.EpisodeMeta
import com.typesafe.scalalogging.Logger

import scala.collection.mutable.ListBuffer

/**
  * EpisodeMetaDAO
  *
  * Created at 2/13/2017.
  *
  * @author zhangxf
  */
class EpisodeMetaDAO extends AbstractDAO[EpisodeMeta] {
  val log = Logger(classOf[SiteDAO])
  override protected val tableName: String = "\"episode_meta\" AS em"
  override protected val columns: String = "em.id,em.episode_id,em.link_id," +
    "em.link_play_time,em.created_at,em.updated_at"

  // LEFT JOIN $linkTableName ON l.id = em.link_id
  private val linkTableName: String = "\"link\" AS l"
  private val linkColumns: String = {
    """l.id          AS l_id
      |,l.play_site  AS l_play_site
      |,l.play_url   AS l_play_url
      |,l.created_at AS l_created_at
      |,l.updated_at AS l_updated_at""".stripMargin.replace('\n', ' ')
  }

  private def collectData(rs: ResultSet): Option[EpisodeMeta] = {
    collectEpisodeMeta(rs, "")
  }

  /**
    * Find all rows.
    *
    * @param conn sql connection
    * @return [[Page]] of [[EpisodeMeta]]
    */
  override def findAll(conn: Connection): Page[EpisodeMeta] = {
    val sql = s"SELECT $columns,$linkColumns FROM $tableName LEFT JOIN $linkTableName ON l.id = em.link_id"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    val rs = st.executeQuery()
    val buffer = new ListBuffer[EpisodeMeta]()

    while (rs.next()) {
      val opt = collectData(rs)
      if (opt.isDefined) buffer += opt.get
    }

    rs.close()
    st.close()
    val elements = buffer.toList
    Page[EpisodeMeta](elements, 1, elements.size, elements.size)
  }

  /**
    * Find by id.
    *
    * @param id   value of `id` column of the row that expected to find
    * @param conn sql connection
    * @return [[Option]] of [[EpisodeMeta]]
    */
  override def findOne(id: Long, conn: Connection): Option[EpisodeMeta] = {
    val sql = s"SELECT $columns,$linkColumns FROM $tableName LEFT JOIN $linkTableName ON l.id = em.link_id WHERE em.id = ?"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    st.setLong(1, id)

    val rs = st.executeQuery()
    val opt = {
      if (rs.next()) collectData(rs)
      else None
    }

    rs.close()
    st.close()
    opt
  }

  /**
    * Insert entity. `entity` id will be ignored if it is not `null`.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return inserted entity
    */
  override def insert(entity: EpisodeMeta, conn: Connection): EpisodeMeta = {
    val columnsNoID = "episode_id,link_id,link_play_time,created_at,updated_at"
    val sql = s"INSERT INTO $tableName ($columnsNoID) VALUES (?,?,?,now(),now()) RETURNING id,created_at,updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    val linkOpt = Option(entity.link)

    st.setLong(1, entity.episodeId)
    if (linkOpt.isDefined) st.setLong(2, linkOpt.get.id)
    else st.setNull(2, Types.BIGINT)
    st.setObject(3, entity.linkPlayTime)

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
  override def update(entity: EpisodeMeta, conn: Connection): EpisodeMeta = {
    val idOpt = Option(entity.id)
    if (idOpt.isEmpty || idOpt.get == 0L) {
      throw new SQLException("ID should not be 0")
    }

    // no created_at
    val updateColumns = "episode_id = ?, link_id = ?, link_play_time = ?, updated_at = now()"
    val sql = s"UPDATE $tableName SET $updateColumns WHERE id = ? RETURNING updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    val linkOpt = Option(entity.link)

    st.setLong(1, entity.episodeId)
    if (linkOpt.isDefined) st.setLong(2, linkOpt.get.id)
    else st.setNull(2, Types.BIGINT)
    st.setObject(3, entity.linkPlayTime)
    st.setLong(4, entity.id)

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

object EpisodeMetaDAO {
  private val dao = new EpisodeMetaDAO()

  def apply(): EpisodeMetaDAO = dao
}
