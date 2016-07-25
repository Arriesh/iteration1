package com.icare.commons.exceptions;

public class ErrorMessage {
	private String strErrorCode;
	private String strErrorDesc;
	private String sIsError;
	private String sPayload;
	
	public String getsPayload() {
		return sPayload;
	}
	public void setsPayload(String sPayload) {
		this.sPayload = sPayload;
	}
	public String getsIsError() {
		return sIsError;
	}
	public void setsIsError(String sIsError) {
		this.sIsError = sIsError;
	}
	public String getStrErrorCode() {
		return strErrorCode;
	}
	public void setStrErrorCode(String strErrorCode) {
		this.strErrorCode = strErrorCode;
	}
	public String getStrErrorDesc() {
		return strErrorDesc;
	}
	public void setStrErrorDesc(String strErrorDesc) {
		this.strErrorDesc = strErrorDesc;
	}
	
	

}
