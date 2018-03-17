package com.asiafrank.bangumi.core

import java.time.OffsetDateTime

import scala.beans.BeanProperty

/**
  * Table
  *
  * Created at 2/6/2017.
  *
  * @author asiafrank
  */
abstract class Table {

  @BeanProperty
  var id: Long = _

  @BeanProperty
  var createdAt: OffsetDateTime = _

  @BeanProperty
  var updatedAt: OffsetDateTime = _
}
