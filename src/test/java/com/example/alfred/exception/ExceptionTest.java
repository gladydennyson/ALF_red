package com.example.alfred.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.context.request.WebRequest;

/**
 * Testing the ExceptionAdvice
 *
 */
public class ExceptionTest {

	/**
	 * Testing the advice for reach, by mocking the exception class and the web
	 * request
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testExceptionAdvice() throws Throwable {

		ExceptionAdvice advice = new ExceptionAdvice();

		// mocking the exception class and the web request
		Exception mockex = Mockito.mock(Exception.class);
		WebRequest mockReq = Mockito.mock(WebRequest.class);

		// setting the mock return of the exception class
		when(mockex.getMessage()).thenReturn("unit test");

		// manually enabling exception logging
		ThreadContext.put("req.exception", "true");

		// getting the length of the exception log file before calling the method
		File log = new File("logs/exception.log");
		long length = log.length();

		// asserting that the returned value is the same as set using mochito
		Assertions.assertEquals(advice.handleAllExceptions(mockex, mockReq).getBody(), "unit test");

		// asserting that the current length of the exception log file is greater after
		// the execution of the exception advice
		assertThat(log.length()).isGreaterThan(length);
	}
}
