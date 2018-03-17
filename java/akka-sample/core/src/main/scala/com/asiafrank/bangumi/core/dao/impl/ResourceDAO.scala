package com.asiafrank.bangumi.core.dao.impl

import java.sql.{Connection, ResultSet, SQLException}
import java.time.OffsetDateTime

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.dao.AbstractDAO
import com.asiafrank.bangumi.core.model.Resource
import com.asiafrank.bangumi.core.util.{ResourceStatus, ResourceType}
import com.typesafe.scalalogging.Logger

import scala.collection.mutable.ListBuffer

/**
  * ResourceDAO
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class ResourceDAO extends AbstractDAO[Resource] {
  val log = Logger(classOf[ResourceDAO])
  override protected val tableName: String = "\"resource\""
  override protected val columns: String = "id,name,url,path,file_size,resource_type,status,site_id,created_at,updated_at"

  /**
    * Collect data from [[ResultSet]] to new instance of [[Resource]]
    *
    * @param rs ResultSet
    * @return instance of [[Resource]]
    */
  private def collectData(rs: ResultSet): Resource = {
    val row = new Resource
    row.id = rs.getLong("id")
    row.name = rs.getString("name")
    row.url = rs.getString("url")
    row.path = rs.getString("path")
    row.fileSize = rs.getLong("file_size")
    row.resourceType = ResourceType.withName(rs.getString("resource_type"))
    row.status = ResourceStatus.withName(rs.getString("status"))
    row.createdAt = rs.getObject("created_at", classOf[OffsetDateTime])
    row.updatedAt = rs.getObject("updated_at", classOf[OffsetDateTime])
    row
  }

  /**
    * Find all rows.
    *
    * @param conn sql connection
    * @return [[Page]] of [[Resource]]
    */
  @throws(classOf[SQLException])
  override def findAll(conn: Connection): Page[Resource] = {
    val sql = s"SELECT $columns FROM $tableName"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    val rs = st.executeQuery()
    val buffer = new ListBuffer[Resource]()
    while (rs.next()) {
      buffer += collectData(rs)
    }

    rs.close()
    st.close()
    val elements = buffer.toList
    Page[Resource](elements, 1, elements.size, elements.size)
  }

  /**
    * Find by id.
    *
    * @param id   value of `id` column of the row that expected to find
    * @param conn sql connection
    * @return [[Option]] of [[Resource]]
    */
  @throws(classOf[SQLException])
  override def findOne(id: Long, conn: Connection): Option[Resource] = {
    val sql = s"SELECT $columns FROM $tableName WHERE id = ?"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    st.setLong(1, id)

    val rs = st.executeQuery()
    val opt = {
      if (rs.next()) Option(collectData(rs))
      else None
    }

    rs.close()
    st.close()
    opt
  }

  /**
    * Insert element. `entity` id will be ignored if it is not `null`.
    * `entity.site.id` should not be 0, so before invoke this method,
    * you should insert the `site` first.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return inserted entity
    */
  @throws(classOf[SQLException])
  override def insert(entity: Resource, conn: Connection): Resource = {
    val columnsNoID = "name,url,path,file_size,resource_type,status,created_at,updated_at"
    val sql = s"INSERT INTO $tableName ($columnsNoID) VALUES (?,?,?,?,?,?,now(),now()) RETURNING id,created_at,updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    st.setString(1, entity.name)
    st.setString(2, entity.url)
    st.setString(3, entity.path)
    st.setLong(4, entity.fileSize)
    st.setString(5, entity.resourceType.toString)
    st.setString(6, entity.status.toString)

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
  override def update(entity: Resource, conn: Connection): Resource = {
    val idOpt = Option(entity.id)
    if (idOpt.isEmpty || idOpt.get == 0L) {
      throw new SQLException("ID should not be 0")
    }

    // no created_at
    val updateColumns = "name = ?,url = ?,path = ?,file_size = ?,resource_type = ?,status = ?,updated_at = now()"
    val sql = s"UPDATE $tableName SET $updateColumns WHERE id = ? RETURNING updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    st.setString(1, entity.name)
    st.setString(2, entity.url)
    st.setString(3, entity.path)
    st.setLong(4, entity.fileSize)
    st.setString(5, entity.resourceType.toString)
    st.setString(6, entity.status.toString)
    st.setLong(7, entity.id)

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

object ResourceDAO {
  private val dao = new ResourceDAO()

  def apply(): ResourceDAO = dao
}