package com.ferlizola.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ferlizola.order.OrderNotFoundException;
import com.ferlizola.person.PersonNotFoundException;
import com.ferlizola.product.ProductNotFoundException;

@ControllerAdvice
@RestController
public class CustomResEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handlerAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(exceptionRes, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public final ResponseEntity<Object> handlerOrdersNotFoundException(OrderNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(exceptionRes, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PersonNotFoundException.class)
	public final ResponseEntity<Object> handlerPersonNotFoundException(PersonNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(exceptionRes, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public final ResponseEntity<Object> handlerProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(exceptionRes, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionRes = new ExceptionResponse(new Date(), ex.getMessage(),
				ex.getBindingResult().toString());

		return new ResponseEntity(exceptionRes, HttpStatus.BAD_REQUEST);
	}
	
	

}
