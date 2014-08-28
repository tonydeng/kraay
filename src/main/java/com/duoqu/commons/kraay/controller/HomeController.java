package com.duoqu.commons.kraay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by tonydeng on 14-8-28.
 */
@Controller
@RequestMapping("/home/*")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("index.do")
    public ModelAndView index() {
        return new ModelAndView("/home/index");
    }
}
