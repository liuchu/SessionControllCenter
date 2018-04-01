package com.chuliu.lte.SessionControllCenter.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: PageController
 * @Description: Controller whom handle all page requests
 * @author Chu LIU
 * @Date 2018/3/31
 */
@Controller
@SpringBootApplication
@RequestMapping("/a")
public class PageController {

    /**
     * @Title: start()
     * @Description: Mapping "/a/start" to related view
     * @return String
     */
    @RequestMapping("/start")
    public String start() {
        return "start";
    }

    /**
     * @Title: manage()
     * @Description: Mapping "/a/manage" to related view
     * @return String
     */
    @RequestMapping("/manage")
    public String manage() {
        return "manage";
    }

}
