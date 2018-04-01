package com.chuliu.lte.SessionControllCenter.util;


import org.springframework.util.ResourceUtils;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: XmlFormatValidator
 * @Description: Provide common methods to validate XML format
 * @author Chu LIU
 * @Date 2018/3/31
 */
public class XmlFormatValidator {

    /**
     * @Title: callPostWithXML
     * @Description: Common method to validate whether a XML string match the rule of a XSD file
     * @param xmlContent XML string content
     * @param xsdFile XSD file
     * @return boolean match or not
     */
    public static boolean validate(String xmlContent,String xsdFile){

        boolean result = false;

        File file= null;

        try {
            file = ResourceUtils.getFile(xsdFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //File file=new File(xsdFile);

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8));
            Source xmlFile = new StreamSource(byteArrayInputStream);
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(file);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            //System.out.println("XML content is valid");
            result = true;
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return result;
    }

}
