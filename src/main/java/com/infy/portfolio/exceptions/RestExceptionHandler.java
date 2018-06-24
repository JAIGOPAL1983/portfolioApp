package com.infy.portfolio.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new PortfolioError(HttpStatus.BAD_REQUEST, error, ex.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(PortfolioError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	   
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(
	           EntityNotFoundException ex) {
		//String message = "Requested Resource Not Found";
		PortfolioError apiError = new PortfolioError(HttpStatus.NOT_FOUND, ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(EstimateValueNotCorrectException.class)
	protected ResponseEntity<Object> handleEstimateNotCorrect(
			EstimateValueNotCorrectException ex) {
		//String message = "Requested Resource Not Found";
		PortfolioError apiError = new PortfolioError(HttpStatus.BAD_REQUEST, ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	
	@ExceptionHandler(ProjectTypeNotValidException.class)
	protected ResponseEntity<Object> handleProjectTypeNotValid(ProjectTypeNotValidException ex) {
		//String message = "Requested Resource Not Found";
		PortfolioError apiError = new PortfolioError(HttpStatus.BAD_REQUEST, ex.getMessage());
	    return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGlobalException(Exception ex) {
		String message = "Oops Technical Error occured !!!";		
		PortfolioError apiError = new PortfolioError(HttpStatus.INTERNAL_SERVER_ERROR, message, getStackTrace(ex));
	    return buildResponseEntity(apiError);
	}
	
	public static String getStackTrace(final Throwable throwable) {
	     final StringWriter sw = new StringWriter();
	     final PrintWriter pw = new PrintWriter(sw, true);
	     throwable.printStackTrace(pw);
	     return sw.getBuffer().toString();
	}
}
