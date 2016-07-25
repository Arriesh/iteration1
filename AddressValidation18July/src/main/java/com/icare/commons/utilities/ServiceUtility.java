package com.icare.commons.utilities;

import java.io.*;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;







public class ServiceUtility {
	
public static Document convertStringToXML(String xmlString){
	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    Document document;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document  = builder.parse( new InputSource( new StringReader( xmlString.replaceAll("\n|\r", "")) ) );  
	        
	        return document;
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	    
	    return null;
}
}

