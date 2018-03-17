package com.asiafrank.bangumi.core.bo

import java.sql.{Connection, SQLException}

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.dao.DAO
import com.asiafrank.bangumi.core.pg.PGContainer
import com.typesafe.scalalogging.Logger

/**
  * AbstractBO - `T` means `Table`, `D` means `DAO`
  *
  * Created at 2/6/2017.
  *
  * @author asiafrank
  */
abstract class AbstractBO[T] extends BO[T] {
  protected def log: Logger
  protected def getDAO: DAO[T]

  val pg = PGContainer.pool

  override def findAll(): Page[T] = {
    todo[Page[T]](c => {
      getDAO.findAll(c)
    })
  }

  override def findOne(id: Long): Option[T] = {
    todo[Option[T]](c => {
      getDAO.findOne(id, c)
    })
  }

  override def insert(entity: T): T = {
    todo[T](c => {
      getDAO.insert(entity, c)
    })
  }

  override def update(entity: T): T = {
    todo[T](c => {
      getDAO.update(entity, c)
    })
  }

  override def delete(id: Long): Int = {
    todo(c => {
      getDAO.delete(id, c)
    })
  }

  override def deleteAll(): Int = {
    todo(c => {
      getDAO.deleteAll(c)
    })
  }

  /*----------------- util methods -----------------*/

  /**
    * Execute `code` with `Connection`. If catch `SQLException`, return `null`
    *
    * @param code a lambda method to execute
    * @tparam R   the return type
    * @return     `R` from `code`
    */
  protected def todo[R](code: Connection => R): R ={
    var conn: Connection = null
    try {
      conn = pg.getConnection
      code(conn)
    } catch {
      case e: SQLException =>
        log.error("", e)
        null.asInstanceOf[R]
    } finally {
      close(conn)
    }
  }

  protected def close(conn: Connection): Unit = {
    if (conn != null) {
      try {
        conn.close()
      } catch {
        case e: SQLException => log.error("", e)
      }
    }
  }
}
