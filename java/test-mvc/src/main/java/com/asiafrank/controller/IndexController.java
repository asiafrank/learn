package com.asiafrank.controller;

import com.asiafrank.start.web.annotation.Controller;
import com.asiafrank.start.web.annotation.RequestMapping;
import com.asiafrank.start.web.constants.RequestMethods;
import com.asiafrank.start.web.utils.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/web")
public class IndexController {
    @RequestMapping(method = RequestMethods.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/");
        ResponseUtils.print(response, "Hello Word");
    }

    @RequestMapping(value = "/test", method = RequestMethods.GET)
    public void test(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/test");
        ResponseUtils.print(response, "test hello");
    }
}