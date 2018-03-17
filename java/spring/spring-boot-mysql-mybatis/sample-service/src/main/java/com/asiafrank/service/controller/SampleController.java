package com.asiafrank.service.controller;

import com.asiafrank.core.base.Expressions;
import com.asiafrank.core.base.Page;
import com.asiafrank.core.base.Pageable;
import com.asiafrank.core.bo.SampleBO;
import com.asiafrank.core.model.Sample;
import com.asiafrank.core.vo.SampleVO;
import com.asiafrank.service.config.Msg;
import com.asiafrank.service.resolver.PageDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * SampleController
 *
 * @author zhangxf Created at 4/6/2017.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public final class SampleController {
    private static final Logger log = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private SampleBO sampleBO;

    @Autowired
    private Msg msg;

    @GET
    public Response home(@QueryParam("id") Long id) {
        Sample s = sampleBO.get(id);
        log.info("home");
        return Response.ok(s).build();
    }

    @GET
    @Path("/query")
    public Response find(@QueryParam("keyword") String keyword) {
        SampleVO vo = new SampleVO();
        vo.and(Expressions.like("name", keyword + "%"));
        List<Sample> samples = sampleBO.find(vo);
        return Response.ok(samples).build();
    }

    @GET
    @Path("/page")
    public Response findPage(@PageDefault Pageable pageable) {
        Page<Sample> samples = sampleBO.find(pageable);
        log.info("{} {} {} {}", pageable, msg.PROJECT_NAME, msg.getLanguage(), msg.getCountry());
        return Response.ok(samples).build();
    }

    @POST
    public Response post(Sample sample) {
        sampleBO.insert(sample);
        log.info("insert: " + sample);
        return Response.ok(sample).build();
    }
}
