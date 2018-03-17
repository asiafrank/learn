package com.asiafrank.bangumi.core.model

import com.asiafrank.bangumi.core.Table

import scala.beans.BeanProperty

/**
  * Link - Other related bangumi play site link.
  * NOTE: bangumiId + playSite should be unique
  *
  * Created at 2/8/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Link extends Table with Serializable {
  @BeanProperty
  var bangumiId: Long = _

  /**
    * Bangumi play site name
    * Example: "哔哩哔哩", "土豆网"
    */
  @BeanProperty
  var playSite: String = _

  /**
    * Bangumi play url
    * Example: http://bangumi.bilibili.com/anime/5792/
    */
  @BeanProperty
  var playUrl: String = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[Link]

  override def equals(other: Any): Boolean = other match {
    case that: Link =>
      (that canEqual this) &&
        id == that.id &&
        playSite == that.playSite &&
        playUrl == that.playUrl &&
        createdAt == that.createdAt &&
        updatedAt == that.updatedAt
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, playSite, playUrl, createdAt, updatedAt)
    state.filter(_ != null).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
