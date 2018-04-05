package com.chuliu.lte.SessionControllCenter.service.impl;

import com.chuliu.lte.SessionControllCenter.enums.SessionActionType;
import com.chuliu.lte.SessionControllCenter.pojo.LTESession;
import com.chuliu.lte.SessionControllCenter.service.SessionService;
import com.chuliu.lte.SessionControllCenter.task.HandleSessionThread;
import com.chuliu.lte.SessionControllCenter.util.HttpServletContextHelper;
import com.chuliu.lte.SessionControllCenter.util.LTESessionIdCreator;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: SessionServiceImpl
 * @Description: Implementation of SessionService
 * @author Chu LIU
 * @Date 2018/3/31
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    private static Logger logger = Logger.getLogger(SessionServiceImpl.class);

    //v1.1, delegate all session threads to a "FixedThreadPool", max threads amount is 50
    private static  ExecutorService service = Executors.newFixedThreadPool(100);

    /**
     * @Title: startSessions
     * @Description: Start multiple sessions
     * @param num how much session to start
     * @param type the type, TMGI or TMGIPool
     * @return void
     */
    @Override
    public void startSessions(int num, String type) {

        for (int i=0;i<num;i++) {

            LTESession lteSession = new LTESession();
            lteSession.setActionType(SessionActionType.START);

            if ("TMGI".equals(type)) {
                lteSession.settMGI("Default TMGI");
            } else if ("TMGIPool".equals(type)){
                lteSession.settMGIPool("Default TMGIPool");
            } else {
                System.err.println("Wrong session type: "+type);
                return;
            }

            //Start multiple threads to send post requests.
            Thread thread = new Thread(new HandleSessionThread(lteSession,"http://localhost:8081/"));
            //v1.1, using ExecutorService to manage threads.
            service.submit(thread);
        }

    }

    /**
     * @Title: startSessions
     * @Description: Stop single session by id
     * @param sessionId how much session to start
     * @return void
     */
    @Override
    public void stopSession(int sessionId) {
        LTESession lteSession = new LTESession();
        lteSession.setActionType(SessionActionType.STOP);
        lteSession.setDeliverySessionId(sessionId);

        Thread thread = new Thread(new HandleSessionThread(lteSession,"http://localhost:8081/"));
        //v1.1, using ExecutorService to manage threads.
        service.submit(thread);

    }

    /**
     * @Title: startSessions
     * @Description: Stop all sessions
     * @return void
     */
    @Override
    public void stopAllSessions() {
        ServletContext context = HttpServletContextHelper.getServletContext();
        ConcurrentHashMap<Integer,LTESession> startedItems =
                (ConcurrentHashMap<Integer,LTESession>) context.getAttribute("startedItems");

        logger.debug("SessionServiceImpl > stopAllSessions");

        //v1.1, "synchronized" is required when iterate
        synchronized (startedItems) {
            for (int sessionId: startedItems.keySet()) {
                logger.debug("foreach"+sessionId);
                stopSession(sessionId);
            }
        }

    }
}
