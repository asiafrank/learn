package com.asiafrank.bangumi.core.util

/**
  * URLs
  *
  * Created at 23/1/2017.
  *
  * @author asiafrank
  */
object URLs {
  private val URL = "\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]".r

  def verify(url: String): Boolean = {
    url match {
      case URL(_*) => true
      case _       => false
    }
  }

  /**
    * Get resource name from url
    *
    * @param url URL String
    * @return resource name
    */
  def resourceName(url: String): String = {
    val option = Option(url)
    if (option.isEmpty) throw new NullPointerException
    val s = option.get
    s.substring(s.lastIndexOf('/') + 1, s.length)
  }
}
