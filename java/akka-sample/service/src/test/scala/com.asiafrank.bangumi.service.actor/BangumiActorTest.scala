package com.asiafrank.bangumi.service.actor

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.asiafrank.bangumi.service.container.ActorContainer
import com.asiafrank.bangumi.service.util.Message
import org.junit.Assert._
import org.junit.Test

/**
  * BangumiActorTest
  *
  * Created at 2/14/2017.
  *
  * @author zhangxf
  */
class BangumiActorTest {
  @Test
  def testActor(): Unit = {
    val system = ActorContainer.system
    val bangumi = ActorContainer.bangumi
    bangumi ! Message.start()

    Thread.sleep(3000L)

    system.terminate()
  }

  @Test
  def testDateFormat(): Unit ={
    val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val date = LocalDate.parse("20170217", dateFormatter)
    assertEquals(2017, date.getYear)
    assertEquals(2, date.getMonthValue)
    assertEquals(17, date.getDayOfMonth)
  }
}
