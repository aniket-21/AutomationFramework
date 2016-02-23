package com.framework.components.handlers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniket on 01/05/15.
 */
public class XMLHandler {

    public static Document getXMLDocument(String filePath){
        try{
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(fXmlFile);
        }
        catch(IOException ex){
            System.out.println("Exception occured while loading the file " + filePath);
            return null;
        }
        catch(Exception ex){
            System.out.println("Exception " +  ex + " occured while parsing Xml " + filePath);
            return null;
        }
    }

    public static Element getElementByName(Document doc, String name){

        try{
            doc.getDocumentElement().normalize();
            XPath xPath =  XPathFactory.newInstance().newXPath();
            String Expression = "//*[@name='" + name + "']";
            return ((Element) xPath.compile(Expression).evaluate(doc, XPathConstants.NODE));
        }
        catch(Exception ex){
            System.out.println("Exception " + ex + "occured while getting an element by ID " + name);
            return null;
        }
    }

    public static List<Element> getChildElements(Element parent){

        NodeList nodeList = null;
        List<Element> childElements = new ArrayList<Element>();

        if(parent.hasChildNodes()){
            nodeList = parent.getChildNodes();
            for(int i=0;i<nodeList.getLength();i++){
                if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
                    childElements.add((Element)nodeList.item(i));
            }
        }

        return childElements;
    }
}
