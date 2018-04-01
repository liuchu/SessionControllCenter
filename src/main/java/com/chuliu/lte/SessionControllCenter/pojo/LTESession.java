package com.chuliu.lte.SessionControllCenter.pojo;

import com.chuliu.lte.SessionControllCenter.enums.SessionActionType;
import com.chuliu.lte.SessionControllCenter.util.LTERestAPI;
import com.chuliu.lte.SessionControllCenter.util.LTESessionIdCreator;

import java.time.LocalDateTime;


/**
 * @ClassName: LTESession
 * @Description: Pojo class to LTE session
 * @author Chu LIU
 * @Date 2018/3/31
 */
public class LTESession {

    private int deliverySessionId = LTESessionIdCreator.generateId();
    private SessionActionType actionType;
    private String tMGIPool = "";
    private String tMGI = "";
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime stopTime = LocalDateTime.now().plusMinutes(5);

    //@Required
    private String version = "v1.0";

    public LTESession() {

    }

    public LTESession(int deliverySessionId, SessionActionType actionType, String tMGIPool,
                      String tMGI, LocalDateTime startTime, LocalDateTime stopTime, String version) {
        this.deliverySessionId = deliverySessionId;
        this.actionType = actionType;
        this.tMGIPool = tMGIPool;
        this.tMGI = tMGI;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.version = version;
    }

    public int getDeliverySessionId() {
        return deliverySessionId;
    }

    public void setDeliverySessionId(int deliverySessionId) {
        this.deliverySessionId = deliverySessionId;
    }

    public SessionActionType getActionType() {
        return actionType;
    }

    public void setActionType(SessionActionType actionType) {
        this.actionType = actionType;
    }

    public String gettMGIPool() {
        return tMGIPool;
    }

    public void settMGIPool(String tMGIPool) {
        this.tMGIPool = tMGIPool;
    }

    public String gettMGI() {
        return tMGI;
    }

    public void settMGI(String tMGI) {
        this.tMGI = tMGI;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "LTESession{" +
                "deliverySessionId=" + deliverySessionId +
                ", actionType=" + actionType +
                ", tMGIPool='" + tMGIPool + '\'' +
                ", tMGI='" + tMGI + '\'' +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", version='" + version + '\'' +
                '}';
    }
}
