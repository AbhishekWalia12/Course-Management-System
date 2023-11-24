package com.cms.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(value= {Exception.class})
	public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(LocalDate.now(), exception.getMessage(), request.getDescription(false),"HTTP 500");
		log.error(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value= {AdmissionInvalidException.class})
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(AdmissionInvalidException exception, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(LocalDate.now(), exception.getMessage(), request.getDescription(false),"HTTP 404");
		log.error(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}	
	
		
	



}


