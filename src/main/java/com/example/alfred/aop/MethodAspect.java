package com.example.alfred.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.alfred.common.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Configuration
public class MethodAspect {

	@Autowired Properties prop;
	final String s="execution(* *..*"+"demo"+"*.*(..))";

	@Around("execution(* *..*Interface*.*(..)) || execution(* *..*Controller*.*(..)) || execution(* *..*Util*.*(..))")
	public Object aroundInterfaces(ProceedingJoinPoint point) throws Throwable {
		ObjectMapper ow = new ObjectMapper();
		System.out.println("----------------"+prop.getEvent()+"---------------------");
		Map<String, Object> message = new HashMap<>();
		message.put("event", "Start");
		message.put("method", point.getSignature().toString());
		message.put("args", point.getArgs());
		System.out.println(ow.writeValueAsString(message));
		System.out.println();

		Object result = point.proceed();

		message = new HashMap<>();
		message.put("event", "Response");
		message.put("method", point.getSignature().toString());
		message.put("response", result);
		System.out.println(ow.writeValueAsString(message));
		System.out.println();
		
		return result;
	}
	
	
	@Around(s)
	public Object test(ProceedingJoinPoint point) throws Throwable {
		System.out.println("in");
		Object result = point.proceed();
		
		return result;
	}
}