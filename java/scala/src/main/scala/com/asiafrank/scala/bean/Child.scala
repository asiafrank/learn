package com.asiafrank.scala.bean

import scala.beans.BeanProperty

/**
  * Child
  *
  * Created at 2017/2/8 0008.
  *
  * @author zhangxf
  */
class Child {
  @BeanProperty
  var id: Long = _

  @BeanProperty
  var name: String = _

  override def toString = s"Child($id, $name)"
}
