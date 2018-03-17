package com.asiafrank.bangumi.core.bo.impl

import com.asiafrank.bangumi.core.bo.AbstractBO
import com.asiafrank.bangumi.core.dao.DAO
import com.asiafrank.bangumi.core.dao.impl.SiteDAO
import com.asiafrank.bangumi.core.model.Site
import com.typesafe.scalalogging.Logger

/**
  * SiteBO
  *
  * Created at 07/2/2017.
  *
  * @author asiafrank
  */
class SiteBO extends AbstractBO[Site] {
  override protected def log = Logger(classOf[SiteBO])
  private val dao = SiteDAO()
  override protected def getDAO: DAO[Site] = dao
}

object SiteBO {
  private val bo = new SiteBO()
  def apply(): SiteBO = bo
}