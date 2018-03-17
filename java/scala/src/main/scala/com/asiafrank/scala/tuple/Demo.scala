package com.asiafrank.scala.tuple

import scala.io.StdIn

/**
  * Demo
  * <p>
  * </p>
  * Created at 1/20/2017.
  *
  * @author zhangxf
  */
object Demo {
  def main(args: Array[String]): Unit = {
    val t = (4, 3l, 2.0, 1l)
    val sum = t._1 + t._2 + t._3 + t._4

    println(s"Sum of elements: $sum")

    t.productIterator.foreach(x => println(s"Value = $x"))

    val t1 = (1, "hello", StdIn)
    println(s"Concatenated String: $t1")

    val t2 = Tuple2("Scala", "hello")
    println(s"Swapped Tuple: ${t2.swap}")
  }
}
