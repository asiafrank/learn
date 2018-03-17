package com.asiafrank.scala.example {
  /**
    * Point
    * <p>
    * </p>
    * Created at 1/19/2017.
    *
    * @author zhangxf
    */
  class Point(val xc: Int, val yc: Int) {
    var x: Int = xc
    var y: Int = yc

    def move(dx: Int, dy: Int): Unit = {
      x = x + dy
      y = y + dy
      println(s"Point x location: $x")
      println(s"Point y location: $y")
    }
  }

  class Location(override val xc: Int,
                 override val yc: Int,
                 val zc: Int) extends Point(xc, yc) {
    var z: Int = zc

    def move(dx: Int, dy: Int, dz: Int): Unit = {
      x = x + dx
      y = y + dy
      z = z + dz
      println(s"Point x location: $x")
      println(s"Point y location: $y")
      println(s"Point z location: $z")
    }
  }

  object Demo {
    def main(args: Array[String]): Unit = {
      val pt = new Point(10, 20)
      pt.move(10, 10)

      val loc = new Location(10, 20, 15)
      loc.move(10, 10, 5)
    }
  }
}