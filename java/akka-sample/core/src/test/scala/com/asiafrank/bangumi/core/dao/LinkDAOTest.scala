package com.asiafrank.bangumi.core.dao

import com.asiafrank.bangumi.core.dao.impl.LinkDAO
import com.asiafrank.bangumi.core.model.Link
import org.junit.Assert._
import org.junit.Test

/**
  * LinkDAOTest
  *
  * Created at 2/13/2017.
  *
  * @author asiafrank
  */
class LinkDAOTest extends AbstractDAOTest {

  @Test
  def test(): Unit = {
    val conn = ds.getConnection
    val l = new Link
    l.playSite = "哔哩哔哩"
    l.playUrl = "http://bangumi.bilibili.com/anime/5792/"
    val linkDAO = LinkDAO()
    val link = linkDAO.insert(l, conn)
    assertEquals(l, link)
    assertTrue(l.id != 0L)

    val page = linkDAO.findAll(conn)
    assertTrue(page.totalElementsNum > 0)

    val siteOpt = linkDAO.findOne(l.id, conn)
    assertTrue(siteOpt.get.playUrl == l.playUrl)

    val affected = linkDAO.delete(l.id, conn)
    assertTrue(affected == 1)
    conn.close()
  }
}
