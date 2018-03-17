package com.asiafrank.bangumi.service.container

import com.asiafrank.bangumi.service.container.ActorContainer._
import com.asiafrank.bangumi.service.util.Message

/**
  * BangumiContainer
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
object BangumiContainer {
  def start(): Unit = {
    bangumi ! Message.start
  }
}
