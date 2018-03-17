package com.asiafrank.learn.akka.example

import akka.actor.Actor
import akka.event.Logging

/**
  * Child
  * <p>
  * </p>
  * Created at 2017/1/19 0019.
  *
  * @author zhangxf
  */
class Child extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case "good" =>
      log.info("received good")
      sender() ! "end"
    case _      => log.info("child received unknown message")
  }
}
