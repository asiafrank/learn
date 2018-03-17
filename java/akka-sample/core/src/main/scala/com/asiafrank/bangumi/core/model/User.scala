package com.asiafrank.bangumi.core.model

import com.asiafrank.bangumi.core.Table
import com.fasterxml.jackson.annotation.JsonIgnore

import scala.beans.BeanProperty

/**
  * User - table `usr`
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class User extends Table with Serializable {

  // TODO: complete it later
  @BeanProperty
  var username: String = _

  @JsonIgnore
  @BeanProperty
  var password: String = _

  override def toString: String = {
    s"""id: $id, username: $username,
       |password: $password, createdAt: $createdAt,
       |updatedAt: $updatedAt""".stripMargin.replace('\n', ' ')
  }
}