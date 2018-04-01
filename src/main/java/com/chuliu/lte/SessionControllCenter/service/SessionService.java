package com.chuliu.lte.SessionControllCenter.service;

/**
 * @ClassName: SessionService
 * @Description: Interface of Session related services
 * @author Chu LIU
 * @Date 2018/3/31
 */
public interface SessionService {

    /**
     * @Title: startSessions
     * @Description: Start multiple sessions
     * @param num how much session to start
     * @param type the type, TMGI or TMGIPool
     * @return void
     */
    void startSessions(int num, String type);

    /**
     * @Title: startSessions
     * @Description: Stop single session by id
     * @param sessionId how much session to start
     * @return void
     */
    void stopSession(int sessionId);

    /**
     * @Title: startSessions
     * @Description: Stop all sessions
     * @return void
     */
    void stopAllSessions();
}
