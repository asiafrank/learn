package com.asiafrank.learn.akka.config

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
  * ConfigExample
  *
  * Created at 2/14/2017.
  *
  * @author zhangxf
  */
object ConfigExample {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load("application.conf")
    val system = ActorSystem("bangumi", config)
    system.terminate()
  }
}
