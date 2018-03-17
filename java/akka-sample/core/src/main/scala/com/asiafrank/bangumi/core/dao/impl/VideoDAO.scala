package com.asiafrank.bangumi.core.dao.impl

import java.sql.{Connection, ResultSet, SQLException}
import java.time.OffsetDateTime

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.dao.AbstractDAO
import com.asiafrank.bangumi.core.model.Video
import com.typesafe.scalalogging.Logger

import scala.collection.mutable.ListBuffer

/**
  * VideoDAO
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class VideoDAO extends AbstractDAO[Video] {
  val log = Logger(classOf[VideoDAO])
  override protected val tableName: String = "\"video\" AS v"
  override protected val columns: String = "v.id,v.name,v.url,v.path,v.file_size,v.resource_type,v.status,v.site_id,v.subtitle_group,v.resolution,v.duration,v.created_at,v.updated_at"

  /**
    * Collect data from [[ResultSet]] to new instance of [[Video]]
    *
    * @param rs ResultSet
    * @return instance of [[Video]]
    */
  protected def collectData(rs: ResultSet): Option[Video] = {
    collectOnlyVideo(rs, "")
  }

  /**
    * Find all rows.
    *
    * @param conn sql connection
    * @return [[Page]] of [[Video]]
    */
  @throws(classOf[SQLException])
  override def findAll(conn: Connection): Page[Video] = {
    val sql = s"SELECT $columns FROM $tableName"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    val rs = st.executeQuery()
    val buffer = new ListBuffer[Video]()
    while (rs.next()) {
      val opt = collectData(rs)
      if (opt.isDefined) buffer += opt.get
    }

    rs.close()
    st.close()
    val elements = buffer.toList
    Page[Video](elements, 1, elements.size, elements.size)
  }

  /**
    * Find by id.
    *
    * @param id   value of `id` column of the row that expected to find
    * @param conn sql connection
    * @return [[Option]] of [[Video]]
    */
  @throws(classOf[SQLException])
  override def findOne(id: Long, conn: Connection): Option[Video] = {
    val sql = s"SELECT $columns FROM $tableName WHERE v.id = ?"
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
    * Insert element. `entity` id will be ignored if it is not `null`.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return inserted entity
    */
  @throws(classOf[SQLException])
  override def insert(entity: Video, conn: Connection): Video = {
    val columnsNoID = "name,url,path,file_size,resource_type,status,subtitle_group,resolution,duration,created_at,updated_at"
    val sql = s"INSERT INTO $tableName ($columnsNoID) VALUES (?,?,?,?,?,?,?,?,?,now(),now()) RETURNING id,created_at,updated_at"
    log.debug("SQL - {}", sql)

    val st = conn.prepareStatement(sql)
    st.setString(1, entity.name)
    st.setString(2, entity.url)
    st.setString(3, entity.path)
    st.setLong(4, entity.fileSize)
    st.setString(5, entity.resourceType.toString)
    st.setString(6, entity.status.toString)
    st.setString(7, entity.subtitleGroup)
    st.setString(8, entity.resolution.toString)
    st.setLong(9, entity.duration)

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
  override def update(entity: Video, conn: Connection): Video = {
    val idOpt = Option(entity.id)
    if (idOpt.isEmpty || idOpt.get == 0L) {
      throw new SQLException("ID should not be 0")
    }

    // no created_at
    val updateColumns = "name = ?,url = ?,path = ?,file_size = ?,resource_type = ?,status = ?," +
      "subtitle_group = ?,resolution = ?,duration = ?,updated_at = now()"
    val sql = s"UPDATE $tableName SET $updateColumns WHERE id = ? RETURNING updated_at"
    log.debug("SQL - {}", sql)

    val st = conn.prepareStatement(sql)
    st.setString(1, entity.name)
    st.setString(2, entity.url)
    st.setString(3, entity.path)
    st.setLong(4, entity.fileSize)
    st.setString(5, entity.resourceType.toString)
    st.setString(6, entity.status.toString)
    st.setString(7, entity.subtitleGroup)
    st.setString(8, entity.resolution.toString)
    st.setLong(9, entity.duration)
    st.setLong(10, entity.id)

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
}

object VideoDAO {
  val dao = new VideoDAO

  def apply(): VideoDAO = dao
}
