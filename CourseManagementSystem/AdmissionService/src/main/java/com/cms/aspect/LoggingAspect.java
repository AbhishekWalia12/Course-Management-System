package com.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class LoggingAspect {
	
	@Pointcut("execution(* com.cms.service.AdmissionServiceImpl.calculateFees(..)) ")
	public void calAdmissionMethod() {}
	@Pointcut("execution(* com.cms.service.AdmissionServiceImpl.deactiveAdmission(..)) ")
	public void delAdmissionMethod() {}
	@Pointcut("execution(* com.cms.service.AdmissionServiceImpl.addFeedback(..)) ")
	public void addFeedbackMethod() {}
	@Pointcut("execution(* com.cms.service.AdmissionServiceImpl.highestFeeForTheRegisteredCourse(..)) ")
	public void highestFeedMethod() {}
	@Pointcut("execution(* com.cms.service.AdmissionServiceImpl.viewFeedbackByCourseId(..)) ")
	public void viewFeedMethod() {}
	
	  @AfterReturning(value = "calAdmissionMethod() || delAdmissionMethod() || addFeedbackMethod() || highestFeedMethod() || viewFeedMethod() ",returning = "result")
	  public void logSuccess(JoinPoint joinPoint) {
	    String methodName = joinPoint.getSignature().getName();
	    log.info("The method {} has completed successfully", methodName);
	  }

	  @AfterThrowing(value = "calAdmissionMethod() || delAdmissionMethod() || addFeedbackMethod() || highestFeedMethod() || viewFeedMethod() ",throwing = "exception" )
	  public void logFailure(Exception exception) { 
	    log.error(exception.getMessage());
	  }
	
}
