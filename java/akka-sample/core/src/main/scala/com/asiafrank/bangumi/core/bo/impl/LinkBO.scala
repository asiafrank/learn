package com.asiafrank.bangumi.core.bo.impl

import com.asiafrank.bangumi.core.bo.AbstractBO
import com.asiafrank.bangumi.core.dao.DAO
import com.asiafrank.bangumi.core.dao.impl.LinkDAO
import com.asiafrank.bangumi.core.model.Link
import com.typesafe.scalalogging.Logger

/**
  * LinkBO
  *
  * Created at 2/15/2017.
  *
  * @author zhangxf
  */
class LinkBO extends AbstractBO[Link] {
  override protected def log = Logger(classOf[LinkBO])
  private val dao = LinkDAO()
  override protected def getDAO: DAO[Link] = dao
}

object LinkBO {
  val bo = new LinkBO
  def apply(): LinkBO = bo
}
