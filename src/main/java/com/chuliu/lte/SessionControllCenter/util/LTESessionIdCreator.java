package com.chuliu.lte.SessionControllCenter.util;

/**
 * @ClassName: LTESessionIdCreator
 * @Description: Generate unique id for LTE Sessions when starting
 * @author Chu LIU
 * @Date 2018/3/31
 */
public class LTESessionIdCreator {

    private static Integer currentId=10000;

    /**
     * @Title: generateId
     * @Description: generate a unique id for new session
     * @return Id of a new session
     */
    public static int generateId(){

        synchronized (currentId) {
            currentId = currentId + 1;
            return currentId;
        }

    }


}


