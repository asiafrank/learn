package com.asiafrank.bangumi.service.controller

import javax.ws.rs._
import javax.ws.rs.core.{MediaType, Response}

import com.asiafrank.bangumi.core.bo.impl.BangumiBO
import com.typesafe.scalalogging.Logger

/**
  * BangumiController
  *
  * Created at 23/1/2017.
  *
  * @author asiafrank
  */
@Path("/")
@Consumes(Array(MediaType.APPLICATION_JSON))
@Produces(Array(MediaType.APPLICATION_JSON))
class BangumiController {
  val log = Logger(classOf[BangumiController])

  val bangumiBO = BangumiBO()

  @GET
  def bangumi(): Response = {
    val list = bangumiBO.findAll(false) // unfinished bangumi list
    Response.ok(list).build
  }
}
