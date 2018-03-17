package com.asiafrank.learn.jersey.controller;

import com.asiafrank.learn.jersey.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * IndexController
 * <p>
 * </p>
 * Created at 12/30/2016.
 *
 * @author zhangxf
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class IndexController {
    @GET
    public Response index() {
        User u = new User();
        u.setName("Frank");
        u.setDate(LocalDateTime.now());
        u.setOffsetDateTime(OffsetDateTime.now());
        return Response.ok().entity(u).build();
    }

    @POST
    public Response save(User u) {
        System.out.println(u.toString());
        return Response.ok().entity(u).build();
    }

    /**
     * request body: [11, 22, 33]
     */
    @PUT
    public Response find(List<Long> ids) {
        return Response.ok().entity(ids).build();
    }
}
