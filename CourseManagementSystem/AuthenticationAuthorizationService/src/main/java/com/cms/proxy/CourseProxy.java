package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cms.model.Course;




@FeignClient(name="courseapp",url="http://localhost:9091")
public interface CourseProxy {
	@PostMapping(value="/course/addCourse")
	public ResponseEntity<Object> addCourse(@RequestBody Course cObj,@RequestHeader HttpHeaders header);
	@PutMapping(value="/course/update/{courseId}/{courseFees}")
	public ResponseEntity<Object> updateCourse(@PathVariable String courseId, @PathVariable("courseFees") Integer fees,@RequestHeader HttpHeaders header);
	@GetMapping(value="/course/viewFeedbackRating/{courseId}")
	public ResponseEntity<Object> findFeedbackRatingForCourseId(@PathVariable String courseId,@RequestHeader HttpHeaders header);
	@PutMapping(value="/course/calculateAverageFeedback/{courseId}/{rating}")
	public ResponseEntity<Object> calculateAverageFeedbackAndUpdate(@PathVariable String courseId,@PathVariable float rating,@RequestHeader HttpHeaders header);
	@DeleteMapping(value="/course/deactivate/{courseId}")
	public ResponseEntity<Object> deactivateCourse(@PathVariable String courseId,@RequestHeader HttpHeaders header);
	@GetMapping(value="/course/viewByCourseId/{courseId}")
	public ResponseEntity<Object> viewByCourseId(@PathVariable String courseId,@RequestHeader HttpHeaders header);
	@GetMapping("/course/viewAll")
	public ResponseEntity<Object> viewAll(@RequestHeader HttpHeaders header);
	
}
