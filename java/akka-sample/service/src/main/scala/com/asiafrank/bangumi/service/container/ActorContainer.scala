package com.asiafrank.bangumi.service.container

import akka.actor.{ActorRef, ActorSystem}
import akka.routing.FromConfig
import com.asiafrank.bangumi.service.actor.{BangumiActor, ResourceCrawlActor}
import com.typesafe.config.ConfigFactory

/**
  * ActorContainer
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
object ActorContainer {
  lazy val system = {
    val config = ConfigFactory.load("akka.conf")
    ActorSystem("bangumi", config)
  }

  lazy val resourceCrawl: ActorRef = system.actorOf(FromConfig.props(ResourceCrawlActor.props), "resource-crawl")
  lazy val bangumi: ActorRef = system.actorOf(BangumiActor.props, "bangumi")
}