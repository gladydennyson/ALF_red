package com.example.alfred.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
			
			String url = "http://localhost:7002/v1/user/akshay/data/submit";
			String requestJson = "{\"name\":\"akshay\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("header", "123");

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			String answer = rest.postForObject(url, entity, String.class);
			System.out.println(answer);
			
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("exception", "true");
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
	//
//			Map<String, Object> temp = new HashMap<String, Object>();
//			temp.put("name", "akshay");
//			rest.exchange("http://localhost:7002/v1/user/akshay/data/submit", HttpMethod.POST, entity, String.class, new HttpEntity<Map<String,Object>>(temp));
			
			headers = new HttpHeaders();
			headers.set("exception", "true");
			headers.setContentType(MediaType.APPLICATION_JSON);

			entity = new HttpEntity<>(headers);

			rest.exchange(prop.getURL(), HttpMethod.GET, entity, String.class);
		}
		return new ResponseEntity<Object>(ex.getMessage(), status);
	}
}