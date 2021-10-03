package com.github.iahrari.springrestmvc.controllers;

import com.github.iahrari.springrestmvc.api.v1.model.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
		extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ErrorDTO> handleClientErrors(HttpClientErrorException e){
		return new ResponseEntity<>(
				new ErrorDTO(e.getMessage()), e.getStatusCode()
		);
	}
}
