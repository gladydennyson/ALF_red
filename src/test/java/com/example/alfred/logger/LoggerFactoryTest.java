package com.example.alfred.logger;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for the logger factory
 *
 */
public class LoggerFactoryTest {

	/**
	 * Declaring the three logger types
	 */
	private EventLogger eventLogger;
	private ExceptionLogger exceptionLogger;
	private HealthLogger healthLogger;

	/**
	 * initializing the loggers before the start of the test
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void init() throws Exception {
		eventLogger = new EventLogger();
		exceptionLogger = new ExceptionLogger();
		healthLogger = new HealthLogger();
	}

	/**
	 * Checking if the logger factory returns the correct classes based on the
	 * values
	 */
	@Test
	public void testGetLogger() {

		// asserts that the logger factory returned an instance of the event logger
		assertThat(new LoggerFactory().getLogger("event")).isInstanceOf(eventLogger.getClass());

		// asserts that the logger factory returned an instance of the health logger
		assertThat(new LoggerFactory().getLogger("health")).isInstanceOf(healthLogger.getClass());

		// asserts that the logger factory returned an instance of the exception logger
		assertThat(new LoggerFactory().getLogger("exception")).isInstanceOf(exceptionLogger.getClass());
	}
}
