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
	
	
	@Pointcut("execution(* com.cms.service.CourseServiceImpl.addCourse(..)) ")
	public void addCourseMethod() {}
	
	@Pointcut("execution(* com.cms.service.CourseServiceImpl.updateCourse(..))")
	public void updateCourseMethod() {}
	
	@Pointcut("execution(* com.cms.service.CourseServiceImpl.viewByCourseId(..))")
	public void viewCourseMethod() {}
	
	@Pointcut("execution(* com.cms.service.CourseServiceImpl.calculateAverageFeedbackAndUpdate(..))")
	public void calculateAverageCourseMethod() {}

	@Pointcut("execution(* com.cms.service.CourseServiceImpl.findFeedbackRatingForCourseId(..))")
	public void feedbackCourseMethod() {}
	
	@Pointcut("execution(* com.cms.service.CourseServiceImpl.deactivateCourse(..))")
	public void deactiveCourseMethod() {}
	
	  @AfterReturning(value = "addCourseMethod() || updateCourseMethod() || viewCourseMethod() || calculateAverageCourseMethod() || feedbackCourseMethod() || deactiveCourseMethod()", returning = "result")
	  public void logSuccess(JoinPoint joinPoint) {
	    String methodName = joinPoint.getSignature().getName();
	    log.info("The method {} has completed successfully", methodName);
	  }

	  @AfterThrowing(value = "addCourseMethod() || updateCourseMethod() || viewCourseMethod() || calculateAverageCourseMethod() || feedbackCourseMethod() || deactiveCourseMethod()", throwing = "exception")
	  public void logFailure(Exception exception) {
	    
	    log.error(exception.getMessage());
	  }
	
	
	
	
}
