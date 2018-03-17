package com.asiafrank.learn.akka.example

import akka.actor.{ActorSystem, Props}

/**
  * Demo
  * <p>
  * </p>
  * Created at 2017/1/19 0019.
  *
  * @author zhangxf
  */
object Demo {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("system")
    val props = Props[Parent]
    val parent = system.actorOf(props)

    parent ! "test"
    parent ! "nothing"

    Thread.sleep(1000L)
    system.terminate()
  }
}

