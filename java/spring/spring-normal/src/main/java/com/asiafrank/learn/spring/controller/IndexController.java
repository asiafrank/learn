package com.asiafrank.learn.spring.controller;

import com.asiafrank.learn.spring.bo.SampleBO;
import com.asiafrank.learn.spring.factory.BOFactory;
import com.asiafrank.learn.spring.model.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * IndexController - for test
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author asiafrank
 */
@RestController
@RequestMapping("/")
public class IndexController {
    private final static Logger log = LoggerFactory.getLogger(IndexController.class);

    private SampleBO sampleBO = BOFactory.instance().getSampleBO();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity index() {
        List<Sample> samples = sampleBO.findAll();
        return ResponseEntity.ok(samples);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create() {
        Sample e1 = new Sample();
        e1.setName("Frank");

        Sample e2 = new Sample();
        e2.setName("Amy");

        List<Sample> samples = Arrays.asList(e1, e2);
        sampleBO.save(samples);
        return ResponseEntity.ok(samples);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        sampleBO.deleteAll();
        return ResponseEntity.ok().build();
    }
}
