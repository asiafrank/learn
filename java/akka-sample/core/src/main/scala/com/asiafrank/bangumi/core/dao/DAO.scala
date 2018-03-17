package com.asiafrank.bangumi.core.dao

import java.sql.{Connection, SQLException}

import com.asiafrank.bangumi.core.Page

/**
  * DAO
  *
  * Created at 2/6/2017.
  *
  * @author asiafrank
  */
trait DAO[T] {
  /**
    * Find all rows.
    *
    * @param conn sql connection
    * @return [[Page]] of [[T]]
    */
  @throws(classOf[SQLException])
  def findAll(conn: Connection): Page[T]

  /**
    * Find by id.
    *
    * @param id   value of `id` column of the row that expected to find
    * @param conn sql connection
    * @return [[Option]] of [[T]]
    */
  @throws(classOf[SQLException])
  def findOne(id: Long, conn: Connection): Option[T]

  /**
    * Insert entity. `entity` id will be ignored if it is not `null`.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return inserted entity
    */
  @throws(classOf[SQLException])
  def insert(entity: T, conn: Connection): T

  /**
    * Update entity. Update all fields, whatever fields are `null`, empty or defined.
    * You'd better invoke [[findOne()]] first, then modify the `entity`,
    * finally invoke this method to make sure the entity fields are all valid.
    *
    * @param entity expected entity
    * @param conn   sql connection
    * @return updated entity
    */
  @throws(classOf[SQLException])
  def update(entity: T, conn: Connection): T

  /**
    * Delete by id.
    *
    * @param id   value of `id` column of the row that expected to delete
    * @param conn sql connection
    * @return affected rows number.
    *         In this case, return `0` means failed, return `1` means success
    */
  @throws(classOf[SQLException])
  def delete(id: Long, conn: Connection): Int

  /**
    * Delete all rows in database.
    *
    * @param conn sql connection
    * @return affected rows number
    */
  @throws(classOf[SQLException])
  def deleteAll(conn: Connection): Int
}
