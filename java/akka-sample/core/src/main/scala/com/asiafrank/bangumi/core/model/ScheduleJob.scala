package com.asiafrank.bangumi.core.model

import com.asiafrank.bangumi.core.Table

import scala.beans.BeanProperty

/**
  * ScheduleJob - table `schedulejob`
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class ScheduleJob extends Table with Serializable {
  // TODO: http://stackoverflow.com/questions/13700452/scheduling-a-task-at-a-fixed-time-of-the-day-with-akka
  // TODO: add time specific
  @BeanProperty
  var name: String = _

  /**
    * List of [[Site]]
    * Schedule to crawl this `sites`
    */
  @BeanProperty
  var sites: List[Site] = _

  @BeanProperty
  var enabled: Boolean = _
}