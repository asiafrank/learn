package com.asiafrank.bangumi.core.model

import java.time.OffsetDateTime

import com.asiafrank.bangumi.core.Table

import scala.beans.BeanProperty

/**
  * EpisodeMeta - Episode play information of [[Link.playSite]]
  *
  * Created at 2/8/2017.
  *
  * @author asiafrank
  */
class EpisodeMeta extends Table with Serializable {
  @BeanProperty
  var episodeId: Long = _

  /**
    * Link of PlaySite
    * many to one
    */
  @BeanProperty
  var link: Link = _

  /**
    * Play time of the [[Bangumi]] in [[Link.playSite]] -
    */
  @BeanProperty
  var linkPlayTime: OffsetDateTime = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[EpisodeMeta]

  override def equals(other: Any): Boolean = other match {
    case that: EpisodeMeta =>
      (that canEqual this) &&
        id == that.id &&
        link == that.link &&
        linkPlayTime == that.linkPlayTime &&
        createdAt == that.createdAt &&
        updatedAt == that.updatedAt
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, link, linkPlayTime, createdAt, updatedAt)
    state.filter(_ != null).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
