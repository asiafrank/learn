package com.asiafrank.bangumi.core.util

import com.asiafrank.bangumi.core.model.Resource

/**
  * ResourceStatus
  * Used by [[Resource]]
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
object ResourceStatus extends Enumeration {
  type ResourceStatus = Value

  /**
    * Waiting to download
    */
  val WAITING = Value

  /**
    * Downloading
    */
  val DOWNLOADING = Value

  /**
    * Done - download completed
    */
  val DONE = Value
}
