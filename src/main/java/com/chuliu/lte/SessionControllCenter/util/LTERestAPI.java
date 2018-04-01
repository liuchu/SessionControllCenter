package com.chuliu.lte.SessionControllCenter.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: LTERestAPI
 * @Description: Provide LTE server API interfaces
 * @author Chu LIU
 * @date 2018/3/31
 */
public class LTERestAPI {

    private HttpClient client;

    public int deliverSession(String url,String data){
        return callPostWithXML(url,data);
    }

    /**
     * @Title: callPostWithXML
     * @Description: Common method to call POST http request with XML HTTP body
     * @param url URL of remote server
     * @param postContent XML content of HTTP body
     * @return int response HTTP status code from remote server
     */
    private int callPostWithXML(String url, String postContent){

        int responseStatusCode = 0;

        if (client == null){
            synchronized (LTERestAPI.class) {
                if(client == null) {
                    client = new DefaultHttpClient();
                }
            }
        }

        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("xml", postContent));

        try {
            post.setEntity(new UrlEncodedFormEntity(parameters,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = client.execute(post);
            responseStatusCode = response.getStatusLine().getStatusCode();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return responseStatusCode;

    }

}
