package com.asiafrank.bangumi.core.bo

import com.asiafrank.bangumi.core.Page

/**
  * BO
  *
  * Created at 2/6/2017.
  *
  * @author asiafrank
  */
trait BO[T] {
  def findAll(): Page[T]

  def findOne(id: Long): Option[T]

  def insert(entity: T): T

  def update(entity: T): T

  def delete(id: Long): Int

  def deleteAll(): Int
}
