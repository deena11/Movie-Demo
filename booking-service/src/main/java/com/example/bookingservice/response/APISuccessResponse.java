package com.example.bookingservice.response;

import java.io.Serializable;

public class APISuccessResponse implements Serializable{
	private String httpStatus;
	private int statusCode;
	private String message;
	private Object body;
	public APISuccessResponse() {
		super();
	}
	public APISuccessResponse(String httpStatus, int statusCode, String message, Object body) {
		super();
		this.httpStatus = httpStatus;
		this.statusCode = statusCode;
		this.message = message;
		this.body = body;
	}
	public String getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("APISuccessResponse [httpStatus=");
		builder.append(httpStatus);
		builder.append(", statusCode=");
		builder.append(statusCode);
		builder.append(", message=");
		builder.append(message);
		builder.append(", body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
