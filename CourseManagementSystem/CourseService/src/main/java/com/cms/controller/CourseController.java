package com.cms.controller;

import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;

import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.proxy.AdmissionProxy;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.service.CourseServiceImpl;

@RestController
public class CourseController {

	@Autowired
	CourseServiceImpl courseServiceImpl;

	@Autowired
	AdmissionProxy proxy;

	@Autowired
	AuthenticationAuthorizationProxy authProxy;

	@PostMapping(value = "/course/addCourse")
	public ResponseEntity<Object> addCourse(@RequestBody Course cObj, @RequestHeader("Authorization") String token) {

		ResponseEntity<Object> result;

		if (authProxy.isValidToken(token)) {
			try {
				Course addedCourse = courseServiceImpl.addCourse(cObj);
				result = ResponseEntity.status(HttpStatus.OK).body(addedCourse);
			} catch (CourseInvalidException e) {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

	@PutMapping(value = "/course/update/{courseId}/{courseFees}")
	public ResponseEntity<Object> updateCourse(@PathVariable String courseId, @PathVariable("courseFees") Integer fees,
			@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if (authProxy.isValidToken(token)) {
			try {
				Course modifiedCourse = courseServiceImpl.updateCourse(courseId, fees);
				result = ResponseEntity.ok(modifiedCourse);
			} catch (CourseInvalidException e) {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

	@GetMapping(value = "/course/viewByCourseId/{courseId}")
	public ResponseEntity<Object> viewByCourseId(@PathVariable String courseId,
			@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;

		if (authProxy.isValidToken(token)) {
			try {
				Course course = courseServiceImpl.viewByCourseId(courseId);
				result = ResponseEntity.status(HttpStatus.OK).body(course);
			} catch (CourseInvalidException e) {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

	@GetMapping(value = "/course/viewFeedbackRating/{courseId}")
	public ResponseEntity<Object> findFeedbackRatingForCourseId(@PathVariable String courseId,
			@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if (authProxy.isValidToken(token)) {
			try {
				float rating = courseServiceImpl.findFeedbackRatingForCourseId(courseId);
				List list = new ArrayList<>();
				list.add(rating);
				list.add(courseId);
				
				result = ResponseEntity.ok(list);
			} catch (CourseInvalidException e) {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

	@PutMapping(value = "/course/calculateAverageFeedback/{courseId}/{rating}")
	public ResponseEntity<Object> calculateAverageFeedbackAndUpdate(@PathVariable String courseId,
			@PathVariable float rating, @RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if (authProxy.isValidToken(token)) {
			try {
				Course course = courseServiceImpl.calculateAverageFeedbackAndUpdate(courseId, rating);
				result = ResponseEntity.ok(course);
			} catch (CourseInvalidException e) {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

	@DeleteMapping(value = "/course/deactivate/{courseId}")
	@Retryable(retryFor = RuntimeException.class, backoff = @Backoff(delay = 100, maxDelay = 101), maxAttempts = 1)
	public ResponseEntity<Object> deactivateCourse(@PathVariable String courseId,
			@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if (authProxy.isValidToken(token)) {
			HttpHeaders header = new HttpHeaders();
			header.set("Authorization", token);
			try {
				Course deactiveCourse = courseServiceImpl.deactivateCourse(courseId);
				proxy.deactiveAdmission(courseId,header);
				result = ResponseEntity.ok(deactiveCourse);
			} catch (CourseInvalidException e) {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} catch (RuntimeException ex) {
				result = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
			}
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return result;
	}

	@GetMapping(value = "/course/viewAll")
	public ResponseEntity<Object> viewAll(@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		boolean validToken = false;
		validToken = authProxy.isValidToken(token);
		if (validToken) {
			List<Course> courseList = courseServiceImpl.viewAll();
			result = ResponseEntity.ok(courseList);
		} else {
			result= ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

}
