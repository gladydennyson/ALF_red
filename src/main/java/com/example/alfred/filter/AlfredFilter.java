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
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Component
public class AlfredFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	// health
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		
		ThreadContext.put("req.id", UuidUtil.getTimeBasedUuid().toString());

		ThreadContext.put("req.port", httpServletRequest.getServerPort() + "");

		ThreadContext.put("req.url", httpServletRequest.getRequestURI()
				+ (httpServletRequest.getQueryString() == null ? "" : ("?" + httpServletRequest.getQueryString())));

		ThreadContext.put("req.method", httpServletRequest.getMethod());

		Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames())
			    .parallelStream()
			    .collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));
		ThreadContext.put("req.headers", new ObjectMapper().writeValueAsString(headers));
		ThreadContext.put("req.exception", headers.getOrDefault("exception", "false"));

		CustomRequestBody wrappedRequest=new CustomRequestBody(httpServletRequest);
		String requestBody = new String(wrappedRequest.getBody());        
		ThreadContext.put("req.requestBody", requestBody);
		
		try {
			filterChain.doFilter(wrappedRequest, httpServletResponse);
		} finally {
			ThreadContext.clearAll();
		}
	}

	@Override
	public void destroy() {

	}
}
