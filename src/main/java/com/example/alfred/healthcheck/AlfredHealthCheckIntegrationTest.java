package com.example.alfred.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class AlfredHealthCheckIntegrationTest {
	@SuppressWarnings("deprecation")
	@Test
    public void systemHealthCheck() {
        HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();
        healthCheckRegistry.register("AlfredHealthChecker", new AlfredHealthCheck());
        assertThat(healthCheckRegistry.getNames().size(), equalTo(1));
        Map<String, HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();
        assertFalse(results.isEmpty());
        results.forEach((k, v) -> assertTrue(v.isHealthy()));
    }
	
}
