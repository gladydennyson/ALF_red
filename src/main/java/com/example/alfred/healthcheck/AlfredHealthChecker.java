package com.example.alfred.healthcheck;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Map;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.example.alfred.common.Properties;
import com.example.alfred.logger.AlfredLogger;
import com.example.alfred.logger.LoggerFactory;

/**
 * Class to execute the health check Checks system health, heap, thread and
 * performance
 *
 */
public class AlfredHealthChecker {

	/**
	 * Logger to log into the health.log file
	 */
	AlfredLogger logger = new LoggerFactory().getLogger("health");

	/**
	 * method that calls the various checks
	 */
	public void executeAlfredHealthChecker() {
		systemHealthCheck(new HealthCheckRegistry());
		heapCheck(ManagementFactory.getMemoryMXBean());
		threadCheck(ManagementFactory.getThreadMXBean());
	}

	/**
	 * Checks the systems health and logs if the system is unhealthy
	 */
	public void systemHealthCheck(HealthCheckRegistry healthCheckRegistry) {

		// runs the system health checks
		healthCheckRegistry.register("AlfredHealthChecker", new AlfredHealthCheck());
		Map<String, HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();

		// logs a warning if the system is not healthy
		if (!results.get("AlfredHealthChecker").isHealthy()) {
			logger.warn("{\"warning\" : \"The application is not healthy\"}");
		}
	}

	/**
	 * Checks the heap usage and the garbage collection
	 */
	public void heapCheck(MemoryMXBean memoryMXBean) {

		// logs a warning if there are objects that are not finalized by the garbage
		// collector
		if (memoryMXBean.getObjectPendingFinalizationCount() > 0) {
			logger.warn("{\"warning\" : \"Some objects were not finalized by the garbage collector\"}");
		}

		// calculates the percentage of the heap used by the application
		double heapUsage = (((double) memoryMXBean.getHeapMemoryUsage().getUsed() / 1073741824) * 100)
				/ ((double) memoryMXBean.getHeapMemoryUsage().getMax() / 1073741824);

		// logs a warning if the heap usage is more than 75%
		if (heapUsage > 75) {
			logger.warn(String.format("{\"warning\" : \"The application is using %.2f percent of the total heap memory\"}",
					heapUsage));
		}
	}

	/**
	 * Checks the threads for deadlocks Checks the performance of each request
	 */
	public void threadCheck(ThreadMXBean threadMXBean) {

		// logs a warning if there are deadlocked threads
		int deadLocked = threadMXBean.findDeadlockedThreads() == null ? 0 : threadMXBean.findDeadlockedThreads().length;
		if (deadLocked > 0) {
			logger.warn(String.format("{\"warning\" : \"The application has %d deadlocked threads.\"}", deadLocked));
		}

		// logs a warning if a request takes more than 300ms to execute
		float time = threadMXBean.getCurrentThreadCpuTime() / 1000000f;
		if (time > 300) {
			logger.warn(String.format("{\"warning\" : \"The url %s is using %.2f ms to execute.\"}",
					new Properties().getURL(), time));
		}
	}

}
