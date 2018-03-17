package com.asiafrank.learn.springboot.controller;

import com.asiafrank.learn.springboot.config.BB;
import com.asiafrank.learn.springboot.config.SampleProperties;
import com.asiafrank.learn.springboot.core.base.Expressions;
import com.asiafrank.learn.springboot.core.base.Page;
import com.asiafrank.learn.springboot.core.base.Pageable;
import com.asiafrank.learn.springboot.core.bo.SampleBO;
import com.asiafrank.learn.springboot.core.model.Sample;
import com.asiafrank.learn.springboot.core.vo.SampleVO;
import com.asiafrank.learn.springboot.resolver.PageDefault;
import com.asiafrank.learn.springboot.util.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * SampleController
 *
 * @author zhangxf Created at 4/6/2017.
 */
@RestController
@RequestMapping(path = "/",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController {
    private static final Logger log = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private SampleBO sampleBO;

    @Autowired
    private SampleProperties properties;

    @Autowired
    private BeanFactory beanFactory;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity home(@RequestParam(value = "id", required = false) Long id) {
        RS rs = new RS();
        if (!isOk(rs, id)) {
            return rs.get();
        }

        Sample s = sampleBO.get(id);
        log.info("home");
        log.info("properties : " + properties.getName());
        return ResponseEntity.ok(s);
    }

    @RequestMapping(path = "/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity find(@RequestParam("keyword") String keyword) {
        SampleVO vo = new SampleVO();
        vo.and(Expressions.like("name", keyword + "%"));
        List<Sample> samples = sampleBO.find(vo);

        BB bb = (BB) beanFactory.getBean("bb", "xxx");
        bb.setA(10);
        log.info(bb.toString());

        BB bb0 = (BB) beanFactory.getBean("bb", "xxx0");
        log.info(bb0.toString());

        String str = sampleBO.queryTest2("good example");
        log.info(str);
        return ResponseEntity.ok(samples);
    }

    @RequestMapping(path = "/page", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity findPage(@PageDefault Pageable pageable) {
        Page<Sample> samples = sampleBO.find(pageable);
        log.info("{}", pageable);
        return ResponseEntity.ok(samples);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity post(@RequestBody Sample sample) {
        sampleBO.insert(sample);
        log.info("insert: " + sample);
        return ResponseEntity.ok(sample);
    }

    @RequestMapping(value = "/tx", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity postTx(@RequestBody Sample sample) {
        try {
            sampleBO.txTest(sample);
        } catch (Exception e) {
            log.error("get exception", e);
        }
        log.info("transaction insert: " + sample);
        return ResponseEntity.ok(sample);
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity postBatch() {
        Type[] t = Type.values();
        int size = t.length;
        List<Sample> samples = new LinkedList<>();
        for (int i = 0; i < 200; i++) {
            Sample s = new Sample();
            s.setName("x-" + i);
            s.setType(t[i%size]);
            samples.add(s);
        }
        sampleBO.batchInsert(samples);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/qt", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity queryTest(Integer x, Integer y) {
        long l = sampleBO.queryTest(x, y);
        return ResponseEntity.ok(l);
    }

    private class RS {
        ResponseEntity entity = null;

        public void set(ResponseEntity entity) {
            this.entity = entity;
        }

        public ResponseEntity get() {
            return entity;
        }
    }

    private boolean isOk(RS rs, Long id) {
        if (Objects.isNull(id)) {
            rs.set(ResponseEntity.badRequest().body("bad"));
            return false;
        } else {
            return true;
        }
    }
}
