package com.asiafrank.learn.jersey;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * App
 * <p>
 * </p>
 * Created at 12/30/2016.
 *
 * @author zhangxf
 */
public final class Main {
    public static void main( String[] args ) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
        App app = new App();
        Server server = JettyHttpContainerFactory.createServer(baseUri, app);
        server.start();
        server.join();
    }
}
