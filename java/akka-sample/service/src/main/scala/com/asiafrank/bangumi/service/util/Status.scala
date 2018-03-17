package com.asiafrank.bangumi.service.util

/**
  * Status
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
object Status extends Enumeration {
  type Status = Value
  val INIT, START, DONE, ERROR = Value
}
