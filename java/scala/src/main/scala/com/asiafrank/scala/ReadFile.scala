package com.asiafrank.scala

import scala.collection.mutable
import scala.io.Source

/**
  * ReadFile
  * <p>
  * </p>
  * Created at 23/1/2017.
  *
  * @author asiafrank
  */
object ReadFile {
  def main(args: Array[String]) {
    val map = mutable.HashMap.empty[String, String]
    val lines = Source.fromResource("sample.properties").getLines
    lines.foreach(s => {
      val t = s.split("=")
      val k = t(0).trim
      val v = t(1).trim
      if (!v.isEmpty)
        map += (k->v)
    })
    map.foreach(u => println(s"${u._1}: ${u._2}"))
  }
}
