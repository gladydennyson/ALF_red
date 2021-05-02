package com.example.alfred.exception;

import java.util.Map;
import java.util.Map.Entry;

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
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableWebMvc
@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ResponseStatus annotation = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
		if (annotation != null) {
			status = annotation.value() != null ? annotation.value() : annotation.code();
		}

		Properties prop = new Properties();
		if (prop.getException()) {
			new ExceptionLogger().error(ex);
		} else {
			String url = "http://localhost:" + prop.getPort() + prop.getURL();
			String body = prop.getRequestBody();
			HttpHeaders headers = new HttpHeaders();
			try {
				@SuppressWarnings("unchecked")
				Map<String, String> headerMap = new ObjectMapper().readValue(prop.getRequestHeaders(), Map.class);
				for (Entry<String, String> k : headerMap.entrySet()) {
					headers.set(k.getKey(), k.getValue());
				}
				headers.set("exception", "true");
				RestTemplate rest = new RestTemplate();
				if (prop.getMethodType().equals("post")) {
					HttpEntity<String> entity = new HttpEntity<String>(body, headers);
					rest.postForObject(url, entity, Object.class);
				} else if (prop.getMethodType().equals("put")) {
					HttpEntity<String> entity = new HttpEntity<String>(body, headers);
					rest.put(url, entity);
				} else if (prop.getMethodType().equals("get")) {
					HttpEntity<String> entity = new HttpEntity<String>(headers);
					rest.exchange(url, HttpMethod.GET, entity, Object.class);
				}
			} catch (Exception e) {
				
			}
		}
		return new ResponseEntity<Object>(ex.getMessage(), status);
	}
}