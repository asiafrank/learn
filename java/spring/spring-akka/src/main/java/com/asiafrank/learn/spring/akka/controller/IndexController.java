package com.asiafrank.learn.spring.akka.controller;

import com.asiafrank.learn.spring.akka.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author zhangxf
 */
@RestController
@RequestMapping
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Result index() {
        return new Result();
    }
}
