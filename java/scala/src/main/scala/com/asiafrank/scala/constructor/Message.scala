package com.asiafrank.scala.constructor

import Status._

/**
  * Message
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class Message[T](val status: Status, val attach: Option[T] = None) {
}

object Message {
  def start = new Message(Status.START)
}