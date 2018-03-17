package com.asiafrank.bangumi.core.dao

import com.asiafrank.bangumi.core.dao.impl.{SiteDAO, VideoDAO}
import com.asiafrank.bangumi.core.model.{Site, Video}
import com.asiafrank.bangumi.core.util.{ResourceStatus, ResourceType, VideoResolution}
import org.junit.Assert._
import org.junit.Test

/**
  * VideoDAOTest
  *
  * Created at 2/10/2017.
  *
  * @author asiafrank
  */
class VideoDAOTest extends AbstractDAOTest {

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

    val v = new Video
    v.name = "超人幻想 第1集 [华盟字幕组]"
    v.url = "/data/torrent/sddssdfsdfsdfsadsdafd.torrent"
    v.resourceType = ResourceType.VIDEO
    v.status = ResourceStatus.WAITING
    v.subtitleGroup = "华盟字幕组"
    v.resolution = VideoResolution._720P
    val videoDAO = VideoDAO()
    var video = videoDAO.insert(v, conn)
    assertTrue(v.id != 0L)
    assertEquals(v, video)

    v.subtitleGroup = "华盟&澄空"
    video = videoDAO.update(v, conn)
    assertEquals(v, video)

    video = videoDAO.findOne(v.id, conn).get
    assertEquals(v, video)

    val page = videoDAO.findAll(conn)
    assertTrue(page.totalElementsNum > 0)

    val affected = videoDAO.delete(v.id, conn)
    assertTrue(affected == 1)
    conn.close()
  }
}
