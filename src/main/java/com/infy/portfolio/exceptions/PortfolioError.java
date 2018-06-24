package com.infy.portfolio.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PortfolioError {

	private HttpStatus status;
	private String message;	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private String timestamp;
	private String debugMessage;
	
	public PortfolioError(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = LocalDateTime.now().toString();
	}
	
	public PortfolioError(HttpStatus status, String message, String debugMessage) {
		this.status = status;
		this.message = message;
		this.debugMessage = debugMessage;
		this.timestamp = LocalDateTime.now().toString();
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDebugMessage() {
		return debugMessage;
	}
	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
}
