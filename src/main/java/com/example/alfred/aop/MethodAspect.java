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

@Aspect
@Configuration
public class MethodAspect {

	@Autowired
	Properties prop;

	@Around("execution(* *..*Interface*.*(..)) || execution(* *..*Controller*.*(..))")
	public Object aroundInterfaces(ProceedingJoinPoint point) throws Throwable {
		ObjectMapper ow = new ObjectMapper();
		Map<String, Object> message = new HashMap<>();
		message.put("event", "Start");
		message.put("method", point.getSignature().toString());
		message.put("args", point.getArgs());
		if (prop.getEvent()) {
			new EventLogger().debug(ow.writeValueAsString(message));
		}
		if (prop.getException()) {
			new ExceptionLogger().debug(ow.writeValueAsString(message));
		}
		Object result = point.proceed();

		message = new HashMap<>();
		message.put("event", "Response");
		message.put("method", point.getSignature().toString());
		message.put("response", result);
		if (prop.getEvent()) {
			new EventLogger().debug(ow.writeValueAsString(message));
		}
		if (prop.getException()) {
			new ExceptionLogger().debug(ow.writeValueAsString(message));
		}

		return result;
	}

}