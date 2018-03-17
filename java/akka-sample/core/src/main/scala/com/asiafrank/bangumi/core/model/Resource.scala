package com.asiafrank.bangumi.core.model

import com.asiafrank.bangumi.core.Table
import com.asiafrank.bangumi.core.util.ResourceStatus.ResourceStatus
import com.asiafrank.bangumi.core.util.ResourceType.ResourceType

import scala.beans.BeanProperty

/**
  * Resource - table `resource`
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Resource extends Table with Serializable {

  @BeanProperty
  var name: String = _

  /**
    * download url
    */
  @BeanProperty
  var url: String = _

  /**
    * downloaded path
    */
  @BeanProperty
  var path: String = _

  /**
    * bytes size
    */
  @BeanProperty
  var fileSize: Long = _

  @BeanProperty
  var resourceType: ResourceType = _

  @BeanProperty
  var status: ResourceStatus = _

  def canEqual(other: Any): Boolean = other.isInstanceOf[Resource]

  override def equals(other: Any): Boolean = other match {
    case that: Resource =>
      (that canEqual this) &&
        id == that.id &&
        name == that.name &&
        url == that.url &&
        path == that.path &&
        fileSize == that.fileSize &&
        resourceType == that.resourceType &&
        status == that.status &&
        createdAt == that.createdAt &&
        updatedAt == that.updatedAt
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, name, url, path, fileSize, resourceType, status, createdAt, updatedAt)
    state.filter(_ != null).map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
