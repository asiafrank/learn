package com.asiafrank.bangumi.core.dao.impl

import java.sql.{Connection, ResultSet, SQLException}
import java.time.OffsetDateTime

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.dao.AbstractDAO
import com.asiafrank.bangumi.core.model.Site
import com.typesafe.scalalogging.Logger

import scala.collection.mutable.ListBuffer

/**
  * SiteDAO
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class SiteDAO extends AbstractDAO[Site] {
  val log = Logger(classOf[SiteDAO])
  override protected val tableName: String = "\"site\""
  override protected val columns: String = "id,name,base_url,seeds,interests,depth,created_at,updated_at"

  /**
    * Collect data from [[ResultSet]] to new instance of [[Site]]
    *
    * @param rs ResultSet
    * @return instance of [[Site]]
    */
  private def collectData(rs: ResultSet): Option[Site] = {
    collectOnlySite(rs, "")
  }

  /**
    * Find all rows.
    *
    * @param conn sql connection
    * @return [[Page]] of [[Site]]
    */
  @throws(classOf[SQLException])
  override def findAll(conn: Connection): Page[Site] = {
    val sql = s"SELECT $columns FROM $tableName"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)

    val rs = st.executeQuery()
    val buffer = new ListBuffer[Site]()
    while (rs.next()) {
      val opt = collectData(rs)
      if (opt.isDefined) buffer += opt.get
    }

    rs.close()
    st.close()
    val elements = buffer.toList
    Page[Site](elements, 1, elements.size, elements.size)
  }

  /**
    * Find by id.
    *
    * @param id   value of `id` column of the row that expected to find
    * @param conn sql connection
    * @return [[Option]] of [[Site]]
    */
  @throws(classOf[SQLException])
  override def findOne(id: Long, conn: Connection): Option[Site] = {
    val sql = s"SELECT $columns FROM $tableName WHERE id = ?"
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
  override def insert(entity: Site, conn: Connection): Site = {
    val columnsNoID = "name,base_url,seeds,interests,depth,created_at,updated_at"
    val sql = s"INSERT INTO $tableName ($columnsNoID) VALUES (?,?,?,?,?,now(),now()) RETURNING id,created_at,updated_at"
    log.debug("SQL - {}", sql)
    val st = conn.prepareStatement(sql)
    val seeds = conn.createArrayOf("text", entity.seeds.toArray)
    val interests = conn.createArrayOf("text", entity.interests.toArray)
    st.setString(1, entity.name)
    st.setString(2, entity.baseUrl)
    st.setArray(3, seeds)
    st.setArray(4, interests)
    st.setInt(5, entity.depth)

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
  override def update(entity: Site, conn: Connection): Site = {
    val idOpt = Option(entity.id)
    if (idOpt.isEmpty || idOpt.get == 0L) {
      throw new SQLException("ID should not be 0")
    }

    val updateColumns = "name = ?,base_url = ?,seeds = ?,interests = ?,depth = ?,updated_at = now()"
    val sql = s"UPDATE $tableName SET $updateColumns WHERE id = ? RETURNING updated_at"
    log.debug("SQL - {}", sql)

    val st = conn.prepareStatement(sql)
    val seeds = conn.createArrayOf("text", entity.seeds.toArray)
    val interests = conn.createArrayOf("text", entity.interests.toArray)
    st.setString(1, entity.name)
    st.setString(2, entity.baseUrl)
    st.setArray(3, seeds)
    st.setArray(4, interests)
    st.setInt(5, entity.depth)
    st.setLong(6, entity.id)

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

object SiteDAO {
  private val dao = new SiteDAO()

  def apply(): SiteDAO = dao
}