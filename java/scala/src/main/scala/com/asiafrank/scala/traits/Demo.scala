package com.asiafrank.scala.traits {
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
      val p1 = new Point(2, 3)
      val p2 = new Point(2, 4)
      val p3 = new Point(3, 3)

      println(p1.isNotEqual(p2))
      println(p1.isNotEqual(p3))
      println(p1.isNotEqual(2))
    }
  }
}