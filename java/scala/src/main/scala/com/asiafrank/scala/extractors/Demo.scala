package com.asiafrank.scala.extractors {
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
      val x= Demo(5)
      println(x)

      x match {
        case Demo(num) => println(s"$x is bigger two times than $num")

        // unapply is invoked
        case _ => println("i cannot calculate")
      }
    }

    def apply(x: Int) = x * 2
    def unapply(z: Int): Option[Int] = {
      println("unapply is invoked")
      if (z % 2 == 0) Some(z / 2) else None
    }
  }
}
