package com.asiafrank.bangumi.core.dao

import com.asiafrank.bangumi.core.dao.impl.EpisodeDAO
import com.asiafrank.bangumi.core.model.Episode
import org.junit.Assert._
import org.junit.Test

/**
  * EpisodeDAOTest
  *
  * Created at 2/13/2017.
  *
  * @author asiafrank
  */
class EpisodeDAOTest extends AbstractDAOTest {

  @Test
  def test(): Unit = {
    val conn = ds.getConnection
    val e = new Episode
    e.num = 2
    val episodeDAO = EpisodeDAO()
    var episode = episodeDAO.insert(e, conn)
    assertEquals(e, episode)
    assertTrue(e.id != 0L)

    val page = episodeDAO.findAll(conn)
    assertTrue(page.totalElementsNum > 0)

    val eOpt = episodeDAO.findOne(e.id, conn)
    assertTrue(eOpt.get.num == e.num)

    e.num = 4
    episode = episodeDAO.update(e, conn)
    assertEquals(e, episode)

    val affected = episodeDAO.delete(e.id, conn)
    assertTrue(affected == 1)
    conn.close()
  }
}
