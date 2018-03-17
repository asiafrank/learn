package com.asiafrank.learn.springboot.controller;

import com.asiafrank.learn.springboot.bo.SampleBO;
import com.asiafrank.learn.springboot.factory.BOFactory;
import com.asiafrank.learn.springboot.model.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status;

/**
 * SampleController
 * <p>
 * </p>
 * Created at 1/11/2017.
 *
 * @author zhangxf
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public final class SampleController {
    private final static Logger log = LoggerFactory.getLogger(SampleController.class);

    private SampleBO sampleBO = BOFactory.instance().getSampleBO();

    @GET
    public Response home() {
        List<Sample> samples = sampleBO.find();
        return Response.status(Status.OK).entity(samples).build();
    }

    @POST
    public Response create() {
        Sample s = new Sample();
        s.setName("Hello");
        s.setDescription("Hello World!");
        sampleBO.create(s);
        return Response.status(Status.OK).entity(s).build();
    }

    @DELETE
    public Response delete() {
        sampleBO.delete();
        return Response.status(Status.OK).build();
    }
}
