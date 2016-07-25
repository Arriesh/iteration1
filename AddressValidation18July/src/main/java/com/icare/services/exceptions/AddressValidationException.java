package com.icare.services.exceptions;

import java.util.*;

import org.mule.module.ws.consumer.SoapFaultException;


import com.icare.commons.exceptions.ErrorMessage;
import com.icare.commons.exceptions.IcareException;
import com.icare.commons.utilities.ServiceUtility;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AddressValidationException {
	
	public ErrorMessage catchException(Exception exc){
		
		ErrorMessage errorMessage = new ErrorMessage();
		
		Map<String,String> hPayloadMap =  new HashMap<String,String>();
		if(exc instanceof SoapFaultException){
			hPayloadMap = ((SoapFaultException) exc).getInfo();
			
			System.out.println(" AddressValidation Payload Map : "+hPayloadMap.get("Payload"));
String xml = hPayloadMap.get("Payload").toString();




	    Document doc;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
			        .parse(new InputSource(new StringReader(xml)));
		

	    NodeList errNodes = doc.getElementsByTagName("detail");
	    if (errNodes.getLength() > 0) {
	        Element err = (Element)errNodes.item(0);
	        System.out.println(err.getElementsByTagName("ExceptionCode")
	             .item(0).getTextContent());
	        
	        errorMessage.setStrErrorCode(err.getElementsByTagName("ErrorId")
		             .item(0).getTextContent());
	        errorMessage.setStrErrorDesc(err.getElementsByTagName("Message")
		             .item(0).getTextContent());
	    } else { 
	        // success
	    }
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return ServiceUtility.convertStringToDocument(hPayloadMap.get("Payload"));
		
		//return ServiceUtility.convertStringToXML(hPayloadMap.get("Payload"));
		
	}
		
		return errorMessage;
	
	}
	
}