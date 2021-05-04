package com.example.alfred.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.alfred.common.Properties;
import com.example.alfred.healthcheck.AlfredHealthChecker;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Filter to intercepts all the http requests
 *
 */
@Configuration
@Component
public class AlfredFilter implements Filter {

	@Autowired
	Properties prop;
	/**
	 * auto-generated
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * Adds the data to be used by the application to the thread context.
	 *
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		// generates a unique id for each request using the timestamp and stores it into
		// the thread context
		ThreadContext.put("req.id", UuidUtil.getTimeBasedUuid().toString());

		// gets the server port and stores it into the thread context
		ThreadContext.put("req.port", httpServletRequest.getServerPort() + "");

		// gets the request url and stores it into the thread context
		ThreadContext.put("req.url", httpServletRequest.getRequestURI()
				+ (httpServletRequest.getQueryString() == null ? "" : ("?" + httpServletRequest.getQueryString())));

		// gets the request method type and stores it into the thread context
		ThreadContext.put("req.method", httpServletRequest.getMethod());

		// gets the request headers and stores it into the thread context
		Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames()).parallelStream()
				.collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));
		ThreadContext.put("req.headers", new ObjectMapper().writeValueAsString(headers));

		// gets the exceptions flag from the headers and stores it into the thread
		// context
		ThreadContext.put("req.exception", headers.getOrDefault("exception", "false"));

		// gets the request body and stores it into the thread context
		CustomRequestBody wrappedRequest = new CustomRequestBody(httpServletRequest);
		String requestBody = new String(wrappedRequest.getBody());
		ThreadContext.put("req.requestBody", requestBody);

		try {
			// moves to the controller
			filterChain.doFilter(wrappedRequest, httpServletResponse);
		} finally {
			if(prop.getHealth())
				new AlfredHealthChecker();
			
			// clear the thread context
			ThreadContext.clearAll();
		}
	}

	/**
	 * auto-generated
	 */
	@Override
	public void destroy() {

	}
}
