package com.asiafrank.bangumi.core.dao

import com.asiafrank.bangumi.core.dao.impl.{ResourceDAO, SiteDAO}
import com.asiafrank.bangumi.core.model.{Resource, Site}
import com.asiafrank.bangumi.core.util.{ResourceStatus, ResourceType}
import org.junit.Assert._
import org.junit.Test

/**
  * ResourceDAOTest
  *
  * Created at 2/10/2017.
  *
  * @author asiafrank
  */
class ResourceDAOTest extends AbstractDAOTest {

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
    val site = siteDAO.insert(s, conn)

    val r = new Resource
    r.name = "超人幻想 第1集 [华盟字幕组]"
    r.url = "/data/torrent/sddssdfsdfsdfsadsdafd.torrent"
    r.resourceType = ResourceType.VIDEO
    r.status = ResourceStatus.WAITING
    val resourceDAO = ResourceDAO()
    var resource = resourceDAO.insert(r, conn)
    assertTrue(r.id != 0L)
    assertEquals(r, resource)

    r.name = "超人幻想 第1集 [华盟&澄空]"
    resource = resourceDAO.update(r, conn)
    assertEquals(r, resource)

    resource = resourceDAO.findOne(r.id, conn).get
    assertEquals(r, resource)

    val page = resourceDAO.findAll(conn)
    assertTrue(page.totalElementsNum > 0)

    val resourceOpt = resourceDAO.findOne(r.id, conn)
    assertTrue(resourceOpt.get.name == r.name)

    val affected = resourceDAO.delete(r.id, conn)
    assertTrue(affected == 1)
    conn.close()
  }
}
