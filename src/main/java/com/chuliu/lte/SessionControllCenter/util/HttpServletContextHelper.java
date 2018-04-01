package com.chuliu.lte.SessionControllCenter.util;

import com.chuliu.lte.SessionControllCenter.enums.SessionActionType;
import com.chuliu.lte.SessionControllCenter.pojo.LTESession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HttpServletContextHelper
 * @Description: Provide method to get ServletContext and initial required data
 * @author Chu LIU
 * @Date 2018/3/31
 */
@WebListener
@Component
public class HttpServletContextHelper implements ServletContextListener {

    private static ServletContext servletContext;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // Do nothing
    }

    /**
     * @Title: contextInitialized
     * @Description: Obtain servletContext as private field and init required project data
     * @param arg0
     * @return void
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        servletContext = arg0.getServletContext();
        initData(servletContext);
    }

    /**
     * @Title: getServletContext
     * @Description: Return servletContext
     * @return ServletContext
     */
    public static ServletContext getServletContext(){
        return servletContext;
    }

    /**
     * @Title: getServletContext
     * @Description: Initial project data detail
     * @param servletContext
     * @return void
     */
    private void initData(ServletContext servletContext){
        Map<Integer,LTESession> startedItems = new HashMap<Integer,LTESession>();
        List<LTESession> startingItems = new ArrayList<LTESession>();
        List<LTESession> failedItems = new ArrayList<LTESession>();

        if (servletContext==null){
            System.out.println("servletContext is NULL !!!");
        } else {
            servletContext.setAttribute("startingItems",startingItems);
            servletContext.setAttribute("startedItems",startedItems);
            servletContext.setAttribute("failedItems",failedItems);
            System.out.println("servletContext data loaded");
        }

    }

}
