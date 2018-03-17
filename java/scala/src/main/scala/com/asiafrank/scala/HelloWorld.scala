package com.asiafrank.scala

import scala.collection.mutable

/**
  * https://www.tutorialspoint.com/scala/index.htm
  */
object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    // closure
    val fun = (i: Int) => i * 10
    println(s"fun(10) ${fun(10)}")

    // string
    val str: StringBuilder = new StringBuilder
    str.append("100000").append("-BigBang")
    println(s"str: ${str.toString()}")

    val str0 = {
      """hello boy,
        |did you know the drama 'BigBang'""".stripMargin
    }
    println(str0)

    val str1 = {
      """hello boy,
        |did you know the drama 'BigBang'
      """.stripMargin.replace('\n', ' ')
    }
    println(str1)

    // array
    val list = Array(1.9, 2.9, 3.4, 3.5)
    list.foreach(x => print(x + " "))
    println()

    def p(x: Double): Unit = print(x + " ")
    list foreach p

    for (x <- list) {
      print(x + " ")
    }
    println()

    val total = list.sum
    println(s"Total is $total")

    val max = list.max
    println(s"Max is $max")

    // multi-dimensional arrays
    val matrix = Array.ofDim[Int](3, 3)
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        matrix(i)(j) = j
      }
    }

    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        print(s" ${matrix(i)(j)}")
      }
      println()
    }

    // range
    val list1 = Array.range(10, 20, 2)
    val list2 = Array.range(10, 20)
    list1.foreach(x => print(s" $x"))
    list2.foreach(x => print(s" $x"))
    println()

    // list
    var list3 = 2 :: 1 :: "bar" :: "foo" :: Nil
    list3 :+= 10 // return another immutable list
    list3 foreach println

    val list4 = new mutable.MutableList[Int]
    list4 += 4

    // optional
    val s: String = null
    val option = Option(s)
    val n = option.fold("s is empty")(x => x)
    println(n)

    val s1 = ""
    val s1Opt = Option(s1)
    println(s1Opt.isEmpty)

    // invoke Java code
    val j = new st
    j.p()

    // use variable with try catch
    var m = null.asInstanceOf[st]
    try {
      m = new st
    } catch {
      case e: Exception => e.printStackTrace()
    }
    println(m.isInstanceOf[st])

    def todo(code: Int => Unit): Unit ={
      val i = 10
      code(i)
    }

    todo(i => {
      println("todo: " + i*1000)
    })
    //================
    // careful with _
    // http://stackoverflow.com/questions/8000903/what-are-all-the-uses-of-an-underscore-in-scala
    //================
  }
}
