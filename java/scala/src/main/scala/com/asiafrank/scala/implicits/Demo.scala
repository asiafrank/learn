package com.asiafrank.scala.implicits {
  import com.asiafrank.scala.implicits.Run._

  import scala.collection.mutable.ListBuffer

  /**
    * Demo
    * http://stackoverflow.com/questions/8257447/how-to-use-implicitly-with-a-function
    * http://stackoverflow.com/questions/3855595/what-is-the-scala-identifier-implicitly
    * http://stackoverflow.com/questions/7888944/what-do-all-of-scalas-symbolic-operators-mean
    *
    * Created at 1/19/2017.
    *
    * @author zhangxf
    */
  object Demo {
    def main(args: Array[String]): Unit = {
      4 times println("hello")

      1 min 2 // see Predef#intWrapper

      import scala.language.implicitConversions
      implicit def listBuffer2List[A](x: ListBuffer[A]): List[A] = {
        println("conversion invoked")
        x.toList
      }

      val buff = new ListBuffer[String]
      buff += "1"
      buff += "2"
      val x: List[String] = buff // implicit convert to List[String]
      println(x)
    }
  }
}