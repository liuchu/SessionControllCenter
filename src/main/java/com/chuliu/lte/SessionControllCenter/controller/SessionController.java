package com.chuliu.lte.SessionControllCenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.chuliu.lte.SessionControllCenter.pojo.LTESession;
import com.chuliu.lte.SessionControllCenter.service.SessionService;
import com.chuliu.lte.SessionControllCenter.service.impl.SessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: SessionController
 * @Description: Controller whom handle all session related requests
 * @author Chu LIU
 * @Date 2018/3/31
 */
@RestController
@SpringBootApplication
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService sessionService;

    /**
     * @Title: startSessions
     * @Description: Start multiple sessions request
     * @param request ServletRequest
     * @return void
     */
    @PostMapping("/start")
    void startSessions(ServletRequest request){

        String startAmount = request.getParameter("startAmount");
        String startType = request.getParameter("startType");

        sessionService.startSessions(Integer.valueOf(startAmount),startType);

    }

    /**
     * @Title: stopSession
     * @Description: Stop single session request
     * @param request ServletRequest
     * @return void
     */
    @PostMapping("/stop")
    void stopSession(ServletRequest request){
        String sessionId = request.getParameter("sessionId");
        sessionService.stopSession(Integer.valueOf(sessionId));
    }

    /**
     * @Title: stopAllSession
     * @Description: Stop all sessions request
     * @param response HttpServletResponse
     * @return void
     */
    @RequestMapping("/stopAll")
    void stopAllSession(HttpServletResponse response){

        System.out.println("Stopping all");

        sessionService.stopAllSessions();

        try {
            response.sendRedirect("/a/start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
