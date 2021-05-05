package com.example.alfred.health;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.SortedMap;
import java.util.TreeMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.example.alfred.healthcheck.AlfredHealthChecker;

/**
 * Testing the health functionalities
 *
 */
public class HealthTest {

	/**
	 * Checks the system health by mocking the Health check registry
	 */
	@Test
	public void system() {

		AlfredHealthChecker checker = new AlfredHealthChecker();

		// mocking health check registry and health check result
		HealthCheckRegistry mockregistry = Mockito.mock(HealthCheckRegistry.class);
		HealthCheck.Result mockCheck = Mockito.mock(HealthCheck.Result.class);

		// mocking system health value to be false to enforce the logging
		when(mockCheck.isHealthy()).thenReturn(false);

		// mocking the result of execution of the check to mockCheck
		SortedMap<String, HealthCheck.Result> mockData = new TreeMap<String, HealthCheck.Result>();
		mockData.put("AlfredHealthChecker", mockCheck);
		when(mockregistry.runHealthChecks()).thenReturn(mockData);

		// getting the length of the health log file before the execution of the method
		File log = new File("logs/health.log");
		long length = log.length();

		// asserting that the health check does not throw any exception
		Assertions.assertThatCode(() -> checker.systemHealthCheck(mockregistry)).doesNotThrowAnyException();

		// asserting that the current length of the health log file is greater after
		// the execution of the method
		assertThat(log.length()).isGreaterThan(length);
	}

	/**
	 * checks the heap health by mocking the MemoryXBBean and MemoryUsage
	 */
	@Test
	public void heap() {

		AlfredHealthChecker checker = new AlfredHealthChecker();

		// mocking the MemoryXBBean and MemoryUsage
		MemoryMXBean mockBean = Mockito.mock(MemoryMXBean.class);
		MemoryUsage mockMemory = Mockito.mock(MemoryUsage.class);

		// setting values to initiate the garbage collection health check log
		when(mockBean.getObjectPendingFinalizationCount()).thenReturn(1);
		when(mockBean.getHeapMemoryUsage()).thenReturn(mockMemory);
		when(mockMemory.getUsed()).thenReturn(1l);
		when(mockMemory.getMax()).thenReturn(2l);

		// getting the length of the health log file before the execution of the method
		File log = new File("logs/health.log");
		long length = log.length();

		// asserting that the heap check method does not throw an exception
		Assertions.assertThatCode(() -> checker.heapCheck(mockBean)).doesNotThrowAnyException();

		// asserting that the current length of the health log file is greater after
		// the execution of the method
		assertThat(log.length()).isGreaterThan(length);

		// setting values to initiate the heap usage health check log
		when(mockBean.getObjectPendingFinalizationCount()).thenReturn(0);
		when(mockMemory.getUsed()).thenReturn(1l);
		when(mockMemory.getMax()).thenReturn(1l);

		// getting the length of the health log file before the execution of the method
		length = log.length();

		// asserting that the heap check method does not throw an exception
		Assertions.assertThatCode(() -> checker.heapCheck(mockBean)).doesNotThrowAnyException();

		// asserting that the current length of the health log file is greater after
		// the execution of the method
		assertThat(log.length()).isGreaterThan(length);

	}

	/**
	 * checks the thread and performance health logs by mocking the ThreadMXBean
	 */
	@Test
	public void thread() {

		AlfredHealthChecker checker = new AlfredHealthChecker();

		// mocking the ThreadMXBean
		ThreadMXBean mockBean = Mockito.mock(ThreadMXBean.class);

		// setting values to initiate the deadlock health check log
		when(mockBean.findDeadlockedThreads()).thenReturn(new long[] { 1, 1 });
		when(mockBean.getCurrentThreadCpuTime()).thenReturn(0l);

		// getting the length of the health log file before the execution of the method
		File log = new File("logs/health.log");
		long length = log.length();

		// asserting that the thread check method does not throw an exception
		Assertions.assertThatCode(() -> checker.threadCheck(mockBean)).doesNotThrowAnyException();

		// asserting that the current length of the health log file is greater after
		// the execution of the method
		assertThat(log.length()).isGreaterThan(length);

		// setting values to initiate the performance health check log
		when(mockBean.findDeadlockedThreads()).thenReturn(null);
		when(mockBean.getCurrentThreadCpuTime()).thenReturn(Long.MAX_VALUE);

		// getting the length of the health log file before the execution of the method
		length = log.length();

		// asserting that the thread check method does not throw an exception
		Assertions.assertThatCode(() -> checker.threadCheck(mockBean)).doesNotThrowAnyException();

		// asserting that the current length of the health log file is greater after
		// the execution of the method
		assertThat(log.length()).isGreaterThan(length);

	}
}
