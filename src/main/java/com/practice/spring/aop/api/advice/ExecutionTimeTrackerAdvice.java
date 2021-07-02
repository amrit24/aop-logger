package com.practice.spring.aop.api.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {
	
	Logger logger=LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);
	
	@Around("@annotation(com.practice.spring.aop.api.advice.TrackExecutionTime)")
	public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
		/*
		Start time for method execution
		 */
		long startTime=System.currentTimeMillis();
		/*
		 Object mapper to print respinse json
		 */
		ObjectMapper mapper = new ObjectMapper();

		/*
		Method details through reflection
		 */
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().toString();
		Object[] array = pjp.getArgs();

		/*
		log method details before execution
		 */
		logger.info("method invoked " + className + " : " + methodName + "()" + "arguments : "
				+ mapper.writeValueAsString(array));

		/*
		Proceed to execution
		 */
		Object obj=pjp.proceed();

		/*
		Log response
		 */
		logger.info(className + " : " + methodName + "()" + "Response : "
				+ mapper.writeValueAsString(obj));

		/*
		Endtime of method execution
		 */
		long endTime=System.currentTimeMillis();

		/*
		Log time taken in method execution
		 */
		logger.info("Method name"+pjp.getSignature()+" time taken to execute : "+(endTime-startTime)+" ms");
		return obj;
	}

}
