package com.chuliu.lte.SessionControllCenter.task;

import com.chuliu.lte.SessionControllCenter.enums.SessionActionType;
import com.chuliu.lte.SessionControllCenter.pojo.LTESession;
import com.chuliu.lte.SessionControllCenter.service.impl.SessionServiceImpl;
import com.chuliu.lte.SessionControllCenter.util.HttpServletContextHelper;
import com.chuliu.lte.SessionControllCenter.util.LTERestAPI;
import com.chuliu.lte.SessionControllCenter.util.XmlFormatValidator;
import org.apache.log4j.Logger;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName: HandleSessionThread
 * @Description: Core thread class to perform Session control tasks
 * @author Chu LIU
 * @Date 2018/3/31
 */

public class HandleSessionThread implements Runnable {

    private static Logger logger = Logger.getLogger(HandleSessionThread.class);

    private LTESession lteSession;
    private String serverURL;
    private DocumentBuilderFactory documentFactory;
    private TransformerFactory transFactory;

    private ServletContext context = HttpServletContextHelper.getServletContext();

    private List<LTESession> startingItems = (List<LTESession>) context.getAttribute("startingItems");
    private List<LTESession> failedItems = (List<LTESession>) context.getAttribute("failedItems");
    private Map<Integer,LTESession> startedItems = (Map<Integer,LTESession>) context.getAttribute("startedItems");

    /**
     * @Title: HandleSessionThread
     * @Description: Unique constructor method
     * @param lteSession LTESession pojo object
     * @param serverURL LTE server URL
     */
    public HandleSessionThread(LTESession lteSession,String serverURL) {

        this.lteSession = lteSession;
        this.serverURL = serverURL;

        //Ensure factory is singleton
        if (documentFactory == null) {
            synchronized (HandleSessionThread.class){
                if (documentFactory == null) {
                    documentFactory = DocumentBuilderFactory.newInstance();
                }
            }
        }

        if (transFactory == null) {
            synchronized (HandleSessionThread.class){
                if (transFactory == null) {
                    transFactory = TransformerFactory.newInstance();
                }
            }
        }

    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     * @Description: Execute pojo object to xml format, and send POST request to LTE server
     */
    @Override
    public void run() {

        SessionActionType type = lteSession.getActionType();
        int sessionId = lteSession.getDeliverySessionId();

        if (type == SessionActionType.START) {

            startingItems.add(lteSession);

            int httpCode = sendRequest();

            if (httpCode == 200) {

                synchronized (startingItems) {
                    startingItems.remove(lteSession); //Remove from starting list
                }

                synchronized (startedItems) {
                    startedItems.put(sessionId,lteSession); //Add to started list
                }

            } else {
                synchronized (startingItems){
                    startingItems.remove(lteSession); //Remove from starting list
                }

                synchronized (startedItems) {
                    failedItems.add(lteSession); //Add to failed list
                }

            }

        } else { //Stop session
            int httpCode = sendRequest();
            if (httpCode == 200) {
                synchronized (startedItems) {
                    startedItems.remove(sessionId); //Remove from started list
                }

            } else {
                //do nothing
            }
        }

    }

    /**
     * @Title: sendRequest
     * @Description: Send session control HTTP request to remote server
     * @return int returned http status code
     */
    private int sendRequest(){

        try {
            if (lteSession.getActionType() == SessionActionType.START) {
                Thread.sleep(new Random().nextInt(10)*1000); //Start, 0~10s delay
            } else {
                //Do nothing
            }

        } catch (InterruptedException e) {
            logger.error("Thread.sleep() occur error!");
            e.printStackTrace();
        }

        String postContent = formatToXML(lteSession);

        if (!XmlFormatValidator.validate(postContent,"C:\\ChuLIU\\Data\\IdeaProjects\\SessionControllCenter\\src\\main\\resources\\templates\\session_http_body_schema.xsd")){
            logger.error("XML has wrong format!");
            return 500;
        }

        int sessionId = lteSession.getDeliverySessionId();

        LTERestAPI lteRestAPI = new LTERestAPI();
        int code = lteRestAPI.deliverSession(serverURL+"nbi/deliverysession?session_id="+sessionId,postContent);

        if(code==200){
            logger.info(serverURL+"nbi/deliverysession?session_id="+sessionId+" -200 "+"content:\n"+postContent);
        } else {
            logger.error(serverURL+"nbi/deliverysession?session_id="+sessionId+" -"+code+" content:\n"+postContent);
        }

        return code;
    }

    /**
     * @Title: formatToXML
     * @Description: Format LTESession pojo to XML
     * @return String, XML result
     */
    private String formatToXML(LTESession session){

        String xmlString = "";

        //By default,
        boolean isTMGIPool = false;

        String tMGIPoolString = session.gettMGIPool();
        String tMGIString = session.gettMGI();

        if (!"".equals(tMGIPoolString) && !"".equals(tMGIString)) {
            System.out.println("ERROR! Can't have tMGIPool and tMGI at single session");
            return "FAIL";
        } else {
            if (!"".equals(isTMGIPool)) {
                isTMGIPool = true;
            }
        }

        try {
            DocumentBuilder builder = documentFactory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlStandalone(true);

            Element deliverySession = document.createElement("DeliverySession");
            deliverySession.setAttribute("Version",session.getVersion());

            Element deliverySessionId = document.createElement("DeliverySessionId");
            Element action = document.createElement("Action");
            Element tMGIPool = document.createElement("TMGIPool");
            Element tMGI = document.createElement("TMGI");
            Element startTime = document.createElement("StartTime");
            Element stopTime = document.createElement("StopTime");

            deliverySessionId.setTextContent(String.valueOf(session.getDeliverySessionId()));
            String actionString = session.getActionType()==SessionActionType.START?"Start":"Stop";
            action.setTextContent(actionString);
            tMGIPool.setTextContent(session.gettMGIPool());
            tMGI.setTextContent(session.gettMGI());
            startTime.setTextContent(String.valueOf(session.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()));
            stopTime.setTextContent(String.valueOf(session.getStopTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()));

            deliverySession.appendChild(deliverySessionId);
            deliverySession.appendChild(action);
            if (isTMGIPool) {
                deliverySession.appendChild(tMGIPool);
            } else {
                deliverySession.appendChild(tMGI);
            }
            deliverySession.appendChild(startTime);
            deliverySession.appendChild(stopTime);

            document.appendChild(deliverySession);

            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);

            // xml transform String
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transformer.transform(domSource, new StreamResult(bos));
            xmlString = bos.toString();
            //System.out.println(xmlString);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return xmlString;
    }

}
