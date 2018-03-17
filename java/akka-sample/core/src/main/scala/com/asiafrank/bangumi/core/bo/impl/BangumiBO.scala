package com.asiafrank.bangumi.core.bo.impl

import java.time.LocalDate

import com.asiafrank.bangumi.core.Page
import com.asiafrank.bangumi.core.bo.AbstractBO
import com.asiafrank.bangumi.core.dao.DAO
import com.asiafrank.bangumi.core.dao.impl.BangumiDAO
import com.asiafrank.bangumi.core.model.Bangumi
import com.typesafe.scalalogging.Logger

import scala.collection.mutable

/**
  * BangumiBO
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
class BangumiBO extends AbstractBO[Bangumi] {
  override protected def log = Logger(classOf[BangumiBO])

  private val dao = BangumiDAO()

  override protected def getDAO: DAO[Bangumi] = dao

  /**
    * Find bangumi by finished.
    *
    * @param finished default false, unfinished bangumi, otherwise true.
    * @return bangumi list
    */
  def findAll(finished: Boolean = false): Page[Bangumi] = {
    todo[Page[Bangumi]](c => {
      dao.findAll(c, finished)
    })
  }

  /**
    * Find Today bangumi list for analyse.
    *
    * @return Map, key: title, value: Bangumi
    */
  def findTodayMap(): Map[String, Bangumi] = {
    todo[Map[String, Bangumi]](c => {
      val map = new mutable.HashMap[String, Bangumi]
      val today = LocalDate.now()
      val page = dao.findByDay(c, today)
      if (page.totalElementsNum > 0) {
        page.elements.foreach(e => {
          map += (e.name -> e)
        })
      }
      map.toMap
    })
  }
}

object BangumiBO {
  private val bo = new BangumiBO()

  def apply(): BangumiBO = bo
}