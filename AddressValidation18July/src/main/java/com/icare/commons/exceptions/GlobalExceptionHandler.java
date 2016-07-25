package com.icare.commons.exceptions;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.StringReader;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mule.module.ws.consumer.SoapFaultException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GlobalExceptionHandler {

		public ErrorMessage handleException(Exception exc){
			
			int iErrorCode=0;
			
			System.out.println("I AM IN");
			
			if(exc instanceof  InterruptedIOException	|| exc instanceof SocketTimeoutException || exc instanceof SocketException			||
			    exc instanceof UnknownHostException 	|| exc instanceof UnknownServiceException 	||
				exc instanceof SSLException 			|| exc instanceof ProtocolException 		||
				exc instanceof ClosedChannelException	 )
			{
				iErrorCode = 500;
			}else if (exc instanceof SoapFaultException){
				
				
				/**Check if SOAP Fault is from AddressValidation**/
				Map<String,Object> chkAddressValidExcMap = checkIfAddressValidationException(exc);
				
				if((boolean)chkAddressValidExcMap.get("isAddressValidationExc") == true)
					return (ErrorMessage)chkAddressValidExcMap.get("ErrorMessage");
					
				
			}
			
			return getErrorMessage(iErrorCode);
				
		}
		
		
		public ErrorMessage getErrorMessage(int iErrorCodePar){
			System.out.println("I AM IN 22222");
			ErrorMessage errorMessage= new ErrorMessage();
			
			
			switch(iErrorCodePar){
				
			case 500:
					errorMessage.setStrErrorCode("500");
					errorMessage.setStrErrorDesc("External System Exception : Target system not accessible. Please try again later.");
					break;
			default:
				errorMessage.setStrErrorCode("600");
				errorMessage.setStrErrorDesc("Internal Server Error.");
			}
			
				
			return errorMessage;
		}
		
		
		
		public Map<String,Object> checkIfAddressValidationException(Exception exc){
			
			ErrorMessage errorMessage = new ErrorMessage();
			Map<String,Object> hAddValidMap = new HashMap<String,Object>();
			
			Map<String,String> hPayloadMap =  new HashMap<String,String>();
			
			try {
				hPayloadMap = ((SoapFaultException) exc).getInfo();
				
				System.out.println(" AddressValidation Payload Map : "+hPayloadMap.get("Payload"));
				String xml = hPayloadMap.get("Payload").toString();




		    Document doc;
			
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				hAddValidMap.put("isAddressValidationExc", false);
				
				
				return hAddValidMap;
				//e.printStackTrace();
			}
			//return ServiceUtility.convertStringToDocument(hPayloadMap.get("Payload"));
			
			//return ServiceUtility.convertStringToXML(hPayloadMap.get("Payload"));
			
			hAddValidMap.put("isAddressValidationExc", true);
			hAddValidMap.put("ErrorMessage", errorMessage);
			
			return hAddValidMap;
		
		}
}
