package com.asiafrank.scala.traits {
  /**
    * Point
    * <p>
    * </p>
    * Created at 2017/1/19 0019.
    *
    * @author zhangxf
    */
  class Point(xc: Int, yc: Int) extends Equal {
    var x: Int = xc
    var y: Int = yc

    override def isEqual(obj: Any): Boolean = {
      obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == y
    }
  }
}