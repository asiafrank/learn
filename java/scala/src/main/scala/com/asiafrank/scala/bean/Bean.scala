package com.asiafrank.scala.bean

import scala.beans.BeanProperty

/**
  * Bean
  * <p>
  * </p>
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class Bean {
  @BeanProperty
  var id: String = _

  @BeanProperty
  var num: Int = _

  @BeanProperty
  var child: Child = _

  override def toString = s"Bean($id, $num, $child)"
}
