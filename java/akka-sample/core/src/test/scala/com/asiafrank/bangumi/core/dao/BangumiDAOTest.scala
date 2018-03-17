package com.asiafrank.bangumi.core.dao

import com.asiafrank.bangumi.core.dao.impl.BangumiDAO
import com.asiafrank.bangumi.core.model.Bangumi
import org.junit.Assert._
import org.junit.Test

/**
  * BangumiDAOTest
  *
  * Created at 2/10/2017.
  *
  * @author asiafrank
  */
class BangumiDAOTest extends AbstractDAOTest {

  @Test
  def test(): Unit = {
    val conn = ds.getConnection
    val b = new Bangumi
    b.name = "Concrete Revolutio～超人幻想～"
    b.alias = "コンクリート・レボルティオ〜超人幻想〜"
    b.originTime = "17:25"
    b.originStation = "东京电视台"
    b.finished = false
    val bangumiDAO = BangumiDAO()
    var bangumi = bangumiDAO.insert(b, conn)
    assertTrue(b.id != 0L)
    assertEquals(b, bangumi)

    b.alias = "コンクリート・レボルティオ〜超人幻想〜|||"
    bangumi = bangumiDAO.update(b, conn)
    assertEquals(b, bangumi)

    bangumi = bangumiDAO.findOne(b.id, conn).get
    assertEquals(b, bangumi)

    val page = bangumiDAO.findAll(conn)
    assertTrue(page.totalElementsNum > 0)

    val affected = bangumiDAO.delete(b.id, conn)
    assertTrue(affected == 1)
    conn.close()
  }
}
