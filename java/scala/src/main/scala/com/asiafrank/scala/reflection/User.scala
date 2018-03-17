package com.asiafrank.scala.reflection

import scala.beans.BeanProperty

/**
  * User - table `usr`
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class User extends Serializable {
  @BeanProperty
  var id: Long = _

  @BeanProperty
  var username: String = _

  @BeanProperty
  var password: String = _

  @BeanProperty
  var createdAt: String = _

  def updatedAt: String = ""
}
