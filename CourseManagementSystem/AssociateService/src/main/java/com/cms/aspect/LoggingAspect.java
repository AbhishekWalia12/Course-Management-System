package com.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Aspect
@Component
@Slf4j
public class LoggingAspect {
	
	@Pointcut("execution(* com.cms.service.AssociateServiceImpl.addAssociate(..))")
	public void addAssociateMethod() {}
	
	@Pointcut("execution(* com.cms.service.AssociateServiceImpl.viewByAssociateId(..))")
	public void viewAssociateMethod() {}
	
	@Pointcut("execution(* com.cms.service.AssociateServiceImpl.updateAssociate(..))")
	public void updateAssociateMethod() {}
	
	  @AfterReturning(value = "addAssociateMethod() || viewAssociateMethod() || updateAssociateMethod()", returning = "result")
	  public void logSuccess(JoinPoint joinPoint) {
	    String methodName = joinPoint.getSignature().getName();
	    log.info("The method {} has completed successfully", methodName);
	  }

	  @AfterThrowing(value = "addAssociateMethod() || viewAssociateMethod() || updateAssociateMethod()", throwing = "exception")
	  public void logFailure(Exception exception) {
	    
	    log.error(exception.getMessage());
	  }
	
}
