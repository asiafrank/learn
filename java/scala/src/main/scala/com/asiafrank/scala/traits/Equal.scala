package com.asiafrank.scala.traits {
  /**
    * Equal
    * <p>
    * </p>
    * Created at 2017/1/19 0019.
    *
    * @author zhangxf
    */
  trait Equal {
    def isEqual(x: Any): Boolean
    def isNotEqual(x: Any): Boolean = !isEqual(x)
  }
}