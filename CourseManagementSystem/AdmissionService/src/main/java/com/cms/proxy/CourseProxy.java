package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="courseapp",url="http://localhost:9091")
public interface CourseProxy {
	@PutMapping(value="/course/calculateAverageFeedback/{courseId}/{rating}")
	ResponseEntity<Object> calculateAverageFeedbackAndUpdate(@PathVariable String courseId,@PathVariable float rating,@RequestHeader HttpHeaders header);
}
