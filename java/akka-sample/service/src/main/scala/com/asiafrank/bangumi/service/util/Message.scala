package com.asiafrank.bangumi.service.util

import Status._

/**
  * Message
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
case class Message[T](status: Status, attach: Option[T] = None) extends Serializable {
  def get = attach.get
}

object Message {
  def start = new Message(START)

  def start[T](attach: T) = new Message(START, Option(attach))

  def done = new Message(DONE)

  def done[T](attach: T) = new Message(DONE, Option(attach))

  def error = new Message(ERROR)

  def error[T](attach: T) = new Message(ERROR, Option(attach))
}