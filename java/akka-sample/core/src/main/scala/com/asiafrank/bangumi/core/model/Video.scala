package com.asiafrank.bangumi.core.model

import com.asiafrank.bangumi.core.util.VideoResolution.VideoResolution

import scala.beans.BeanProperty

/**
  * Video - table `video` inherits `resource`
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Video extends Resource with Serializable {

  // name = Bangumi name + episode + subtitle-group
  // Example: "超人幻想 第1集 [华盟字幕组]"

  /**
    * Example: "华盟字幕组"
    */
  @BeanProperty
  var subtitleGroup: String = _

  /**
    * _720P | _1080P
    */
  @BeanProperty
  var resolution: VideoResolution = _

  /**
    * nanoseconds
    * 1 seconds = 1000 milliseconds
    * 1 milliseconds = 1000 microseconds
    * 1 microseconds = 1000 nanoseconds
    */
  @BeanProperty
  var duration: Long = _

  override def canEqual(other: Any): Boolean = other.isInstanceOf[Video]

  override def equals(other: Any): Boolean = other match {
    case that: Video =>
      super.equals(that) &&
        id == that.id &&
        (that canEqual this) &&
        subtitleGroup == that.subtitleGroup &&
        resolution == that.resolution &&
        duration == that.duration &&
        createdAt == that.createdAt &&
        updatedAt == that.updatedAt
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(super.hashCode(), subtitleGroup, resolution, duration)
    state.filter(_ != null).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}