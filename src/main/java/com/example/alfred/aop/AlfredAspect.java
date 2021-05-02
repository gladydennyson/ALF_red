package com.example.alfred.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.alfred.common.Properties;
import com.example.alfred.logger.EventLogger;
import com.example.alfred.logger.ExceptionLogger;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class implementing aspect oriented programming
 *
 */
@Aspect
@Configuration
public class AlfredAspect {

	@Autowired
	Properties prop;

	/**
	 * Executes around all the controllers and interfaces in the application context following the Spring naming conventions.
	 * 
	 * if event logging is enables, logs the method name, method arguments, event type, a unique id and the method response from the pointcut
	 * 
	 * if exception flag is enabled, logs the method name, method arguments, event type, a unique id and the method response from the pointcut
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* *..*Interface*.*(..)) || execution(* *..*Controller*.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		
		//logs the data using event and the exception logger based on the flags
		if (prop.getEvent() || prop.getException()) {
			
			// 
			ObjectMapper ow = new ObjectMapper();
			Map<String, Object> message = new HashMap<>();
			message.put("requestid", prop.getRequestID());
			message.put("event", "Start");
			message.put("method", point.getSignature().toString());
			message.put("args", point.getArgs());
			
			if(prop.getEvent()) {
				new EventLogger().debug(ow.writeValueAsString(message));
			}
			if(prop.getException()) {
				new ExceptionLogger().debug(ow.writeValueAsString(message));
			}
		}
		
		//proceed to the method
		Object result = point.proceed();

		
		if (prop.getEvent() || prop.getException()) {
			ObjectMapper ow = new ObjectMapper();
			Map<String, Object> message = new HashMap<>();
			message.put("requestid", prop.getRequestID());
			message.put("event", "Response");
			message.put("method", point.getSignature().toString());
			message.put("response", result);
			if(prop.getEvent()) {
				new EventLogger().debug(ow.writeValueAsString(message));
			}
			if(prop.getException()) {
				new ExceptionLogger().debug(ow.writeValueAsString(message));
			}
		}

		return result;
	}

}