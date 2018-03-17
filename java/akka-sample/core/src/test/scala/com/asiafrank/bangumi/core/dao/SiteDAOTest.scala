package com.asiafrank.bangumi.core.dao

import com.asiafrank.bangumi.core.dao.impl.SiteDAO
import com.asiafrank.bangumi.core.model.Site
import com.asiafrank.bangumi.core.util.ResourceType
import org.junit.Assert._
import org.junit.Test

/**
  * SiteDAOTest
  *
  * Created at 2/10/2017.
  *
  * @author asiafrank
  */
class SiteDAOTest extends AbstractDAOTest {

  @Test
  def test(): Unit = {
    val conn = ds.getConnection
    val s = new Site
    s.name = "动漫花园"
    s.baseUrl = "http://share.dmhy.org/"
    s.seeds = List("http://share.dmhy.org/page-a", "http://share.dmhy.org/page-b")
    s.interests = List(ResourceType.TORRENT)
    s.depth = 2
    val siteDAO = SiteDAO()
    var site = siteDAO.insert(s, conn)
    assertEquals(s, site)
    assertTrue(s.id != 0L)

    val page = siteDAO.findAll(conn)
    assertTrue(page.totalElementsNum > 0)

    val siteOpt = siteDAO.findOne(s.id, conn)
    assertTrue(siteOpt.get.name == s.name)

    s.name = "动漫花园|||"
    site = siteDAO.update(s, conn)
    assertEquals(s, site)

    val affected = siteDAO.delete(s.id, conn)
    assertTrue(affected == 1)
    conn.close()
  }
}
