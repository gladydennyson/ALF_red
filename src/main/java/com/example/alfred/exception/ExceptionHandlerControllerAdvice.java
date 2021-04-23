package com.example.alfred.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(AspectException.class)
	@ResponseBody
    public final ResponseEntity<Object> handleAllAspectExceptions(AspectException ex, WebRequest request) {
        System.out.println(1);
        return new ResponseEntity<Object>(ex.getMessage(), ex.getExceptionStatus());
    }
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        System.out.println(2);
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}