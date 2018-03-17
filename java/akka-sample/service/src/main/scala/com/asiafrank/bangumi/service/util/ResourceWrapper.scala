package com.asiafrank.bangumi.service.util

import com.asiafrank.bangumi.core.util.ResourceType.ResourceType

/**
  * ResourceWrapper
  *
  * Created at 2/17/2017.
  *
  * @author zhangxf
  */
class ResourceWrapper(val bangumiId: Long,
                      val resourceType: ResourceType,
                      val url: String) {
}

object ResourceWrapper {
  def apply(bangumiId: Long,
            resourceType: ResourceType,
            url: String): ResourceWrapper = new ResourceWrapper(bangumiId, resourceType, url)
}
