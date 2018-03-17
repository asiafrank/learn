package com.asiafrank.bangumi.core.dao

import java.time.{OffsetDateTime, ZoneOffset}

import com.asiafrank.bangumi.core.dao.impl.{EpisodeDAO, EpisodeMetaDAO}
import com.asiafrank.bangumi.core.model.{Episode, EpisodeMeta}
import org.junit.Assert._
import org.junit.Test

/**
  * EpisodeMetaDAOTest
  *
  * Created at 2/13/2017.
  *
  * @author asiafrank
  */
class EpisodeMetaDAOTest extends AbstractDAOTest {

  @Test
  def test(): Unit = {
    val conn = ds.getConnection
    val e = new Episode
    e.num = 2
    val episodeDAO = EpisodeDAO()
    val episode = episodeDAO.insert(e, conn)

    val now = OffsetDateTime.now()
    val em = new EpisodeMeta
    em.setEpisodeId(episode.id)
    em.linkPlayTime = now
    val episodeMetaDAO = EpisodeMetaDAO()
    var episodeMeta = episodeMetaDAO.insert(em, conn)
    assertEquals(em, episodeMeta)
    assertTrue(em.id != 0L)

    val page = episodeMetaDAO.findAll(conn)
    assertTrue(page.totalElementsNum > 0)

    val eOpt = episodeMetaDAO.findOne(em.id, conn)
    val left = eOpt.get.linkPlayTime.withOffsetSameInstant(ZoneOffset.UTC)
    val right = em.linkPlayTime.withOffsetSameInstant(ZoneOffset.UTC)
    assertTrue(left == right)

    em.linkPlayTime = now.plusHours(3L)
    episodeMeta = episodeMetaDAO.update(em, conn)
    assertEquals(em, episodeMeta)

    val affected = episodeMetaDAO.delete(em.id, conn)
    assertTrue(affected == 1)
    conn.close()
  }
}
