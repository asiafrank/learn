package com.asiafrank.bangumi.service

import javax.ws.rs.core.UriBuilder

import org.eclipse.jetty.server.Server
import org.glassfish.jersey.jetty.JettyHttpContainerFactory

/**
  * Main
  *
  * Created at 23/1/2017.
  *
  * @author asiafrank
  */
object Main extends App {
  val baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build()
  val app: JserseyApp = new JserseyApp
  val server: Server = JettyHttpContainerFactory.createServer(baseUri, app)
  server.start()
  server.join()
}
