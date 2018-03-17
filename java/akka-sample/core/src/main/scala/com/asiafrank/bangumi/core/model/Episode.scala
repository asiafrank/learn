package com.asiafrank.bangumi.core.model

import com.asiafrank.bangumi.core.Table

import scala.beans.BeanProperty

/**
  * Episode - [[Bangumi]] episode
  *
  * Created at 2/8/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Episode extends Table with Serializable {
  @BeanProperty
  var bangumiId: Long = _

  /**
    * Number of Episode
    */
  @BeanProperty
  var num: Int = _

  /**
    * Downloaded video
    */
  @BeanProperty
  var video: Video = _

  /**
    * Episode play information of [[Link.playSite]]
    * one to many
    */
  @BeanProperty
  var meta: List[EpisodeMeta] = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[Episode]

  override def equals(other: Any): Boolean = other match {
    case that: Episode =>
      (that canEqual this) &&
        id == that.id &&
        num == that.num &&
        video == that.video &&
        meta == that.meta &&
        createdAt == that.createdAt &&
        updatedAt == that.updatedAt
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, num, video, meta, createdAt, updatedAt)
    state.filter(_ != null).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
