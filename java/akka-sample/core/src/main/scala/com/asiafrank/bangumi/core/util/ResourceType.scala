package com.asiafrank.bangumi.core.util

import com.asiafrank.bangumi.core.model.Site

/**
  * ResourceType
  *
  * Created at 24/1/2017.
  *
  * @author asiafrank
  */
object ResourceType extends Enumeration {
  type ResourceType = Value

  /**
    * Anime torrent type. Only focus on 720p and 1080p
    * When [[Site]] interest in this,
    * crawl the `torrent data` in the `Site`.
    * Data will saved in
    */
  val TORRENT = Value
  // TODO: write corresponding `torrent strategies`

  /**
    * Anime picture type include PNG, JPG.
    * When [[Site]] interest in this,
    * crawl the `PICTURE` in the `Site`.
    */
  val PICTURE = Value
  // TODO: write corresponding `PICTURE strategies`

  val MP3 = Value
  // TODO: write corresponding `MP3 strategies`

  val VIDEO = Value
  // TODO: write corresponding `VIDEO strategies`
}
