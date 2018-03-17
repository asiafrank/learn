package com.asiafrank.bangumi.core.bo.impl

import com.asiafrank.bangumi.core.bo.AbstractBO
import com.asiafrank.bangumi.core.dao.DAO
import com.asiafrank.bangumi.core.dao.impl.PictureDAO
import com.asiafrank.bangumi.core.model.Picture
import com.typesafe.scalalogging.Logger

/**
  * PictureBO
  *
  * Created at 2/17/2017.
  *
  * @author zhangxf
  */
class PictureBO extends AbstractBO[Picture] {
  override protected def log = Logger(classOf[PictureBO])
  private val dao = PictureDAO()
  override protected def getDAO: DAO[Picture] = dao
}

object PictureBO {
  private val bo = new PictureBO
  def apply(): PictureBO = bo
}
