package com.example.alfred.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.alfred.common.Properties;
import com.example.alfred.logger.ExceptionLogger;

@EnableWebMvc
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@Autowired
	Properties prop;

	@Autowired
	RestTemplate rest;

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ResponseStatus annotation = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
		if (annotation != null) {
			status = annotation.value() != null ? annotation.value() : annotation.code();
		}
		if (prop.getException()) {
			new ExceptionLogger().error(ex);
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.set("exception", "true");

			HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

			rest.exchange(prop.getURL(), HttpMethod.GET, entity, String.class);
		}
		return new ResponseEntity<Object>(ex.getMessage(), status);
	}
}