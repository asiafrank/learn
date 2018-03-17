package com.asiafrank.scala.implicits {
  /**
    * Run
    * <p>
    * </p>
    * Created at 1/19/2017.
    *
    * @author zhangxf
    */
  object Run {
    implicit class IntTimes(x: Int) {
      def times[A](f: =>A): Unit = {
        def loop(current: Int): Unit = {
          if (current > 0) {
            f
            loop(current - 1)
          }
        }
        loop(x)
      }
    }
  }
}