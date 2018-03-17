package com.asiafrank.bangumi.core.model

import scala.beans.BeanProperty

/**
  * MP3 - table `mp3` inherits `resource`
  *
  * Created at 07/2/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class MP3 extends Resource with Serializable {

  /**
    * nanoseconds
    * 1 seconds = 1000 milliseconds
    * 1 milliseconds = 1000 microseconds
    * 1 microseconds = 1000 nanoseconds
    */
  @BeanProperty
  var duration: Long = _

  // TODO: add other fields
}