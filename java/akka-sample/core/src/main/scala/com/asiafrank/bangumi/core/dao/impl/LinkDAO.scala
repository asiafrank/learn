package com.asiafrank.bangumi.core.dao.impl

import java.sql.{Connection, ResultSet, SQLException, Types}
import java.time.OffsetDateTime

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.dao.AbstractDAO
import com.asiafrank.bangumi.core.model.Link
import com.typesafe.scalalogging.Logger

import scala.collection.mutable.ListBuffer

/**
  * LinkDAO
  *
  * Created at 2/13/2017.
  *
  * @author zhangxf
  */
class LinkDAO extends AbstractDAO[Link] {
  val log = Logger(classOf[LinkDAO])
  override protected val tableName: String = "\"link\" AS l"
  override protected val columns: String = "id,bangumi_id,play_site,play_url,created_at,updated_at"

  private def collectData(rs: ResultSet): Option[Link] = {
    collectOnlyLink(rs, "")
  }

  /**
    * Find all rows.
    *
    * @param conn sql connection
    * @return [[Page]] of [[Link]]
    */
  override def findAll(conn: Connection): Page[Link] = {
    val sql = s"SELECT $columns FROM $tableName"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    val rs = st.executeQuery()
    val buffer = new ListBuffer[Link]()
    while (rs.next()) {
      val opt = collectData(rs)
      if (opt.isDefined) buffer += opt.get
    }

    rs.close()
    st.close()
    val elements = buffer.toList
    Page[Link](elements, 1, elements.size, elements.size)
  }

  /**
    * Find by id.
    *
    * @param id   value of `id` column of the row that expected to find
    * @param conn sql connection
    * @return [[Option]] of [[Link]]    */
  override def findOne(id: Long, conn: Connection): Option[Link] = {
    val sql = s"SELECT $columns FROM $tableName WHERE l.id = ?"
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
  override def insert(entity: Link, conn: Connection): Link = {
    val columnsNoID = "bangumi_id,play_site,play_url,created_at,updated_at"
    val sql = s"INSERT INTO $tableName ($columnsNoID) VALUES (?,?,?,now(),now()) RETURNING id,created_at,updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    val optBangumiId = Option(entity.bangumiId)
    if (optBangumiId.isDefined && optBangumiId.get != 0L)
      st.setLong(1, optBangumiId.get)
    else st.setNull(1, Types.BIGINT)
    st.setString(2, entity.playSite)
    st.setString(3, entity.playUrl)

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
  override def update(entity: Link, conn: Connection): Link = {
    val idOpt = Option(entity.id)
    if (idOpt.isEmpty || idOpt.get == 0L) {
      throw new SQLException("ID should not be 0")
    }

    val updateColumns = "bangumi_id = ?, play_site = ?, play_url = ?, updated_at = now()"
    val sql = s"UPDATE $tableName SET $updateColumns WHERE id = ? RETURNING updated_at"
    log.debug("SQL - {}", sql)

    val st = conn.prepareStatement(sql)
    val optBangumiId = Option(entity.bangumiId)
    if (optBangumiId.isDefined && optBangumiId.get != 0L)
      st.setLong(1, optBangumiId.get)
    else st.setNull(1, Types.BIGINT)
    st.setString(2, entity.playSite)
    st.setString(3, entity.playUrl)
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

object LinkDAO {
  private val dao = new LinkDAO()

  def apply(): LinkDAO = dao
}
