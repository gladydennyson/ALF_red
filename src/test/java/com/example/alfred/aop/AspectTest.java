package com.example.alfred.aop;

import static org.mockito.Mockito.when;

import org.aspectj.lang.ProceedingJoinPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.support.AopUtils;

import com.example.alfred.logger.AbstractLoggerFactory;
import com.example.alfred.logger.AlfredLogger;
import com.example.alfred.logger.LoggerFactory;

/**
 * Unit tests for the AlfredAspect class
 *
 */
public class AspectTest {

	/**
	 * Testing if proxy can be injected into the aspect using the aspectjproxyfactory
	 */
	@Test
	public void testAspect() {
		AbstractLoggerFactory<AlfredLogger> target = new LoggerFactory();
		AspectJProxyFactory factory = new AspectJProxyFactory(target);
		AlfredAspect aspect = new AlfredAspect();
		factory.addAspect(aspect);
		AbstractLoggerFactory<AlfredLogger> proxy = factory.getProxy();
		
		// asserts that the proxy is valid
		assert (AopUtils.isAopProxy(proxy));
	}

	/**
	 * testing if the aspect method works after mocking the ProceedingJoinPoint
	 * @throws Throwable
	 */
	@Test
	public void testAroundAspect() throws Throwable {
		AlfredAspect aspect =new AlfredAspect();
		
		// mocking the proceedingjoinpoint
		ProceedingJoinPoint mockPoint=Mockito.mock(ProceedingJoinPoint.class);
		
		// returning true for the proceed call
		when(mockPoint.proceed()).thenReturn(true);
		
		// asserting that the mocked result is returened bu the around method
		Assertions.assertThat(aspect.around(mockPoint)).isNotEqualTo(false);
		Assertions.assertThat(aspect.around(mockPoint)).isEqualTo(true);		
	}
}
