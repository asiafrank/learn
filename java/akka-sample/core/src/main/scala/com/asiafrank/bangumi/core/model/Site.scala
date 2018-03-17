package com.asiafrank.bangumi.core.model

import com.asiafrank.bangumi.core.Table
import com.asiafrank.bangumi.core.util.ResourceType.ResourceType

import scala.beans.BeanProperty

/**
  * Site - table `site`
  *
  * Created at 23/1/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Site extends Table with Serializable {

  /**
    * bilibili, tudou, anitama
    */
  @BeanProperty
  var name: String = _

  /**
    * The base url of this [[Site]]
    * Example: http://www.bilibili.com
    */
  @BeanProperty
  var baseUrl: String = _

  /**
    * Crawl target
    */
  @BeanProperty
  var seeds: List[String] = _

  /**
    * Interests ResourceTypes to crawl
    */
  @BeanProperty
  var interests: List[ResourceType] = _

  /**
    * Crawl depth
    */
  @BeanProperty
  var depth: Int = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[Site]

  override def equals(other: Any): Boolean = other match {
    case that: Site =>
      (that canEqual this) &&
        id == that.id &&
        name == that.name &&
        baseUrl == that.baseUrl &&
        seeds == that.seeds &&
        interests == that.interests &&
        depth == that.depth &&
        createdAt == that.createdAt &&
        updatedAt == that.updatedAt
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, name, baseUrl, seeds, interests, depth, createdAt, updatedAt)
    state.filter(_ != null).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = s"Site($id, $name, $baseUrl, $seeds, $interests, $depth, $createdAt, $updatedAt)"
}