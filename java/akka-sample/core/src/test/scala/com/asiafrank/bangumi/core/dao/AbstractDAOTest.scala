package com.asiafrank.bangumi.core.dao

import com.asiafrank.bangumi.config.Config
import org.junit.Before
import org.postgresql.ds.PGSimpleDataSource

/**
  * AbstractDAOTest
  *
  * Created at 2/10/2017.
  *
  * @author asiafrank
  */
abstract class AbstractDAOTest {
  protected var ds: PGSimpleDataSource = _

  @Before
  def init(): Unit = {
    ds = new PGSimpleDataSource
    ds.setServerName(Config.PG_SERVER)
    ds.setPortNumber(Config.PG_PORT)
    ds.setDatabaseName(Config.PG_DATABASE)
    ds.setUser(Config.PG_USER)
    ds.setPassword(Config.PG_PASSWORD)
  }
}
