package com.example.alfred.exception;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.alfred.common.Properties;
import com.example.alfred.logger.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Exception handler using Aspect oriented programming to handle and enable
 * exceptions
 *
 */
@EnableWebMvc
@ControllerAdvice
public class ExceptionAdvice {

	/**
	 * Catches all the exceptions thrown by the controllers
	 * 
	 * Handles internal exceptions with a response of (HTTP 500) and custom
	 * exceptions with the code specified using the @ResponseStatus()
	 * 
	 * if the exception flag is enabled, logs the exception or Calls the
	 * resendRequest() method
	 * 
	 * @param ex
	 * @param request
	 * @return Response Entity with the exception message and the HttpStatus
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

		// default Http response status
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		// checks the response status set by @ResponseStatus()
		ResponseStatus annotation = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
		if (annotation != null) {
			status = annotation.value() != null ? annotation.value() : annotation.code();
		}

		// logs the exception if the exception flag is enabled
		Properties prop = new Properties();
		if (prop.getException()) {
			new LoggerFactory().getLogger("exception").error(ex);
		} else {
			// calls the same URL again with the exception flag set
			resendRequest(ex, prop);
		}

		// returns a Response Entity with the exception message and the HttpStatus
		return new ResponseEntity<Object>(ex.getMessage(), status);
	}

	/**
	 * Calls the same URL that encountered the exception enabling the exception flag
	 * 
	 * @param ex
	 */
	private void resendRequest(Exception ex, Properties prop) {

		// generates the URL
		String url = "http://localhost:" + prop.getPort() + prop.getURL();

		// gets the request body from the thread context
		String body = prop.getRequestBody();
		try {

			// gets the headers of the current request from the thread context
			HttpHeaders headers = new HttpHeaders();
			@SuppressWarnings("unchecked")
			Map<String, String> headerMap = new ObjectMapper().readValue(prop.getRequestHeaders(), Map.class);
			for (Entry<String, String> k : headerMap.entrySet()) {
				headers.set(k.getKey(), k.getValue());
			}
			// adds the exception header to the headers
			headers.set("exception", "true");

			// calls the url
			callURL(prop.getMethodType(), url, headers, body);

		} catch (Exception e) {
			// catches the exception for the object mapper
			new LoggerFactory().getLogger("exception").error(e);
		}
	}

	@Async
	public void callURL(String methodType, String url, HttpHeaders headers, String body) {

		try {
			// rest template to call the urls
			RestTemplate rest = new RestTemplate();

			// calling post urls
			if (methodType.equals("post")) {
				rest.postForObject(url, new HttpEntity<String>(body, headers), Object.class);
			}
			// calling put urls
			else if (methodType.equals("put")) {
				rest.put(url, new HttpEntity<String>(body, headers));
			}
			// calling get urls
			else if (methodType.equals("get")) {
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				rest.exchange(url, HttpMethod.GET, entity, Object.class);
			}
		} catch (Exception e) {
			// catches the redundant exception thrown by the rest calls
			// indicates the success of exception logging
		}

	}
}