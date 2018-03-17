package com.asiafrank.bangumi.core.bo.impl

import com.asiafrank.bangumi.core.bo.AbstractBO
import com.asiafrank.bangumi.core.dao.DAO
import com.asiafrank.bangumi.core.dao.impl.ResourceDAO
import com.asiafrank.bangumi.core.model.Resource
import com.typesafe.scalalogging.Logger

/**
  * ResourceBO
  *
  * Created at 07/2/2017.
  *
  * @author asiafrank
  */
class ResourceBO extends AbstractBO[Resource] {
  override protected def log = Logger(classOf[ResourceBO])
  private val dao = ResourceDAO()
  override protected def getDAO: DAO[Resource] = dao
}

object ResourceBO {
  private val bo = new ResourceBO()
  def apply(): ResourceBO = bo
}