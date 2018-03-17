package com.asiafrank.scala.regex {

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
      val pattern = "[S|s]cala".r
      val str = "Scala is scalable and cool"

      println((pattern findAllIn str).mkString(","))

      val U = """\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]""".r

      val s = "http://www.bilibili.com"

      verify(s)
      def verify(url: String): Unit = {
        url match {
          case U(_*) => println("yes")
          case _     => println("no")
        }
      }

      val opt = Option(s) match {
        case Some(s) => println("some")
        case None => throw new NullPointerException
      }
    }
  }
}