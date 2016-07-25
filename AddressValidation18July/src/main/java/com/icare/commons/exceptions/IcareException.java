package com.icare.commons.exceptions;

public abstract class IcareException extends Exception {

		public ErrorMessage errorMessage;
		
		public abstract Object handleException(Exception exc);
}
