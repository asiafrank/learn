package com.asiafrank.bangumi.core.bo.impl

import com.asiafrank.bangumi.core.bo.AbstractBO
import com.asiafrank.bangumi.core.dao.DAO
import com.asiafrank.bangumi.core.dao.impl.VideoDAO
import com.asiafrank.bangumi.core.model.Video
import com.typesafe.scalalogging.Logger

/**
  * SiteBO
  *
  * Created at 07/2/2017.
  *
  * @author asiafrank
  */
class VideoBO extends AbstractBO[Video] {
  override protected def log = Logger(classOf[VideoBO])
  private val dao = VideoDAO()
  override protected def getDAO: DAO[Video] = dao
}

object VideoBO {
  private val bo = new VideoBO()
  def apply(): VideoBO = bo
}