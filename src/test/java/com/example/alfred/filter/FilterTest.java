package com.example.alfred.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Testing the AlfredFilter
 *
 */
public class FilterTest {

	/**
	 * 
	 * Testing by mocking the Request, responce, filter chain and config
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void testFilter() throws ServletException, IOException {

		AlfredFilter filter = new AlfredFilter();

		// mocking Request, responce, filterchain and filterconfig
		HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
		FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
		FilterConfig mockFilterConfig = Mockito.mock(FilterConfig.class);

		// mocking the values used by the filter
		// url
		Mockito.when(mockReq.getRequestURI()).thenReturn("/");
		// port
		Mockito.when(mockReq.getServerPort()).thenReturn(1234);
		// method type
		Mockito.when(mockReq.getMethod()).thenReturn("GET");
		// headers
		Mockito.when(mockReq.getHeaderNames()).thenReturn(new Enumeration<String>() {

			@Override
			public String nextElement() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasMoreElements() {
				// TODO Auto-generated method stub
				return false;
			}
		});
		// get header by name
		Mockito.when(mockReq.getHeader("")).thenReturn("");
		// gets the request headers and stores it into the thread context

		// the get reader method
		Mockito.when(mockReq.getReader()).thenReturn(new BufferedReader(new StringReader("")));

		// Initializing the filter with mocked configurations 
		filter.init(mockFilterConfig);
		CustomRequestBody temp=new CustomRequestBody(mockReq);
		
		// asserting that the filter does not throw any exception
		Assertions.assertThatCode(() -> filter.doFilter(temp, mockResp, mockFilterChain)).doesNotThrowAnyException();

		// destroy the filter
		filter.destroy();
	}
}
