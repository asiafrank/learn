package com.asiafrank.bangumi.core.pg

import com.asiafrank.bangumi.config.Config
import org.postgresql.ds.PGPoolingDataSource

/**
  * PGContainer
  *
  * Created at 31/1/2017.
  *
  * @author asiafrank
  */
object PGContainer {

  /**
    * PostgresConfig
    *
    * Created at 26/1/2017.
    *
    * @author asiafrank
    */
  private val server: String = Config.PG_SERVER

  private val database: String = Config.PG_DATABASE

  private val port: Int = Config.PG_PORT

  private val user: String = Config.PG_USER

  private val password: String = Config.PG_PASSWORD

  private val connInit: Int = Config.PG_CONN_INIT

  private val connMax: Int = Config.PG_CONN_MAX

  lazy val pool: PGPoolingDataSource = {
    val ds = new PGPoolingDataSource
    ds.setDataSourceName("default")
    ds.setServerName(server)
    ds.setPortNumber(port)
    ds.setDatabaseName(database)
    ds.setUser(user)
    ds.setPassword(password)
    ds.setInitialConnections(connInit)
    ds.setMaxConnections(connMax)
    ds
  }
}
