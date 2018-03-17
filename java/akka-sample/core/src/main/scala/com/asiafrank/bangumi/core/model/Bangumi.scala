package com.asiafrank.bangumi.core.model

import java.time.LocalDate

import com.asiafrank.bangumi.core.Table

import scala.beans.BeanProperty

/**
  * Bangumi - table `bangumi`
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Bangumi extends Table with Serializable {
  /**
    * Anime name [zh]
    */
  @BeanProperty
  var name: String = _

  /**
    * Anime alias [jp]
    */
  @BeanProperty
  var alias: String = _

  /**
    * Play time of [[Bangumi.originStation]]
    * Example: "17:25"
    */
  @BeanProperty
  var originTime: String = _

  /**
    * Anime TV Station
    * Example: "东京电视台"
    */
  @BeanProperty
  var originStation: String = _

  /**
    * Cover of Bangumi
    */
  @BeanProperty
  var cover: Picture = _

  /**
    * Latest episode
    */
  @BeanProperty
  var latestEpisode: Episode = _

  /**
    * Episodes of the Bangumi
    * one to many
    */
  @BeanProperty
  var episodes: List[Episode] = _

  /**
    * Other related video site link.
    * Example: "哔哩哔哩", "土豆"
    * one to many
    */
  @BeanProperty
  var links: List[Link] = _

  /**
    * Is finished. - 是否完结
    */
  @BeanProperty
  var finished: Boolean = _

  /**
    * Play date - when in monday 00:00, it should plus 7
    * Example: 20170215 in `web` is "WED"
    */
  @BeanProperty
  var playAt: LocalDate = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[Bangumi]

  override def equals(other: Any): Boolean = other match {
    case that: Bangumi =>
      (that canEqual this) &&
        id == that.id &&
        name == that.name &&
        alias == that.alias &&
        originStation == that.originStation &&
        latestEpisode == that.latestEpisode &&
        episodes == that.episodes &&
        links == that.links &&
        finished == that.finished &&
        createdAt == that.createdAt &&
        updatedAt == that.updatedAt
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, name, alias, originStation, latestEpisode, episodes, links, finished, createdAt, updatedAt)
    state.filter(_ != null).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
