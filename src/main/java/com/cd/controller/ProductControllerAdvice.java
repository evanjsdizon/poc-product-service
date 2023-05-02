package com.cd.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cd.controller.dto.ErrorResponse;
import com.cd.controller.dto.ValidationError;
import com.cd.dto.LogDto;
import com.cd.dto.LogLevel;
import com.cd.exception.ProductNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ProductControllerAdvice {

	private Logger log = LoggerFactory.getLogger(ProductControllerAdvice.class); 

	@Produce("direct:addLog")
	private ProducerTemplate loggingProducer;
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> getNotFoundException(Exception e) {
		var response = new ErrorResponse("404", "NOT_FOUND");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> getMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		var validationErrors = new ArrayList<ValidationError>();
		for (FieldError fe : e.getFieldErrors()) {
			validationErrors.add(new ValidationError(fe.getField(), fe.getDefaultMessage()));
		}
		var response = new ErrorResponse("400", "VALIDATION_ERROR", validationErrors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> getConstraintViolationException(ConstraintViolationException e) {
		var validationErrors = new ArrayList<ValidationError>();
		for (ConstraintViolation<?> ce : e.getConstraintViolations()) {
			validationErrors.add(new ValidationError(ce.getPropertyPath().toString(), ce.getMessage()));
		}
		var response = new ErrorResponse("400", "VALIDATION_ERROR", validationErrors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> getException(Exception e) {
		log.error(e.getMessage(), e);
		try {
			loggingProducer.sendBody(new LogDto(LocalDateTime.now(), LogLevel.ERROR, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace())));
		} catch (CamelExecutionException cee) {
			log.error("Error while sending log to topic: " + e.getMessage(), e);
		}
		var response = new ErrorResponse("500", "INTERNAL_SERVER_ERROR");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
