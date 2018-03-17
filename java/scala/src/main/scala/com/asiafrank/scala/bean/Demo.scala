package com.asiafrank.scala.bean

/**
  * Demo
  * <p>
  * </p>
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
object Demo {
  def main(args: Array[String]) {
    val b = new Bean
    b.setId("123123")
    println(b.id)
    println(b.child)

    val opt = Option(b.child)
    val c = new Child
    c.id = 1L
    c.name = "asiafrank"
    b.child = opt.getOrElse(c)
    println(b.child)
  }
}
