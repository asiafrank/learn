package com.asiafrank.bangumi.core.model

import scala.beans.BeanProperty

/**
  * Picture - table `picture` inherits `resource`
  *
  * Created at 07/2/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Picture extends Resource with Serializable {
  /**
    * pixels
    */
  @BeanProperty
  var width: Long = _

  /**
    * pixels
    */
  @BeanProperty
  var height: Long = _
}