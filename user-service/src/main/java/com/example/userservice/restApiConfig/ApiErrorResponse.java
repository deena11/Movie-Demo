package com.example.userservice.restApiConfig;

import java.io.Serializable;

public class ApiErrorResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	private Object httpStatus;

	private boolean success;

	private boolean error;

	private Object cause;
	
	private Object exceptionMessage;

	public ApiErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApiErrorResponse(String message, Object httpStatus, boolean success, boolean error, Object cause,
			Object exceptionMessage) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.success = success;
		this.error = error;
		this.cause = cause;
		this.exceptionMessage = exceptionMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Object httpStatus) {
		this.httpStatus = httpStatus;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getCause() {
		return cause;
	}

	public void setCause(Object cause) {
		this.cause = cause;
	}

	public Object getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(Object exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "ApiErrorResponse [message=" + message + ", httpStatus=" + httpStatus + ", success=" + success
				+ ", error=" + error + ", cause=" + cause + ", exceptionMessage=" + exceptionMessage + "]";
	}

	

}
