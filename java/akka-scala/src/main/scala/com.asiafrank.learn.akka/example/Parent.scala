package com.asiafrank.learn.akka.example

import akka.actor.{Actor, ActorRef, PoisonPill, Props, Terminated}
import akka.event.Logging

/**
  * MyActor
  * <p>
  * </p>
  * Created at 1/19/2017.
  *
  * @author zhangxf
  */
class Parent extends Actor {
  val log = Logging(context.system, this)
  val child: ActorRef = context.system.actorOf(Props(new Child))
  context.watch(child)

  override def receive: Receive = {
    case "test" =>
      log.info("received test")
      child ! "good"
    case "end"  => end()
    case Terminated(a) => log.info("terminated: {}", a.path)
    case m      => log.info("parent received unknown message: {}", m)
  }

  private def end(): Unit ={
    log.info("end")
    child ! PoisonPill
  }
}