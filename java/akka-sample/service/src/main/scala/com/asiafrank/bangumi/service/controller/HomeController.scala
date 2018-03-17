package com.asiafrank.bangumi.service.controller

import java.time.OffsetDateTime
import javax.ws.rs._
import javax.ws.rs.core.{MediaType, Response}

import com.asiafrank.bangumi.core.bo.impl.BangumiBO
import com.asiafrank.bangumi.core.model.User
import com.typesafe.scalalogging.Logger

/**
  * HomeController
  *
  * Created at 23/1/2017.
  *
  * @author asiafrank
  */
@Path("/")
@Consumes(Array(MediaType.APPLICATION_JSON))
@Produces(Array(MediaType.APPLICATION_JSON))
class HomeController {
  val log = Logger(classOf[HomeController])

  val bangumiBO = BangumiBO

  @GET
  def home(@QueryParam("name") name: String): Response = {
    println(name)

    val dateTime = OffsetDateTime.now

    val u = new User
    u.setId(1000L)
    u.setUsername(name)
    u.setPassword(name + "dfasdfa")
    u.setCreatedAt(dateTime)
    u.setUpdatedAt(dateTime)
    Response.ok(u).build
  }

  @POST
  def create(u: User): Response = {
    println(u)
    Response.ok(u).build
  }

  @PUT
  def update(u: User): Response = {
    println(u)
    Response.ok(u).build
  }

  @DELETE
  def delete(id: Long): Response = {
    Response.ok(id).build
  }
}
