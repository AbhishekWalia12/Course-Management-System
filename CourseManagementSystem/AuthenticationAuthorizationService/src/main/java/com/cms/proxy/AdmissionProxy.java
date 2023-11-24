package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.servlet.http.HttpServletRequest;

@FeignClient(name="admissionapp",url="http://localhost:9093")
public interface AdmissionProxy {
	@GetMapping("/admission/viewAll")
	public ResponseEntity<Object> viewAll(@RequestHeader HttpHeaders header);
	@PostMapping(value="/admission/register/{associateId}/{courseId}")
	public ResponseEntity<Object> registerAssociateForCourse(@PathVariable String associateId,@PathVariable String courseId,@RequestHeader HttpHeaders header);
    @PutMapping(value="/admission/calculateFees/{associateId}")
    public ResponseEntity<Object> calculateFees(@PathVariable String associateId,@RequestHeader HttpHeaders header);
    @GetMapping(value="/admission/highestFee/{associateId}")
    public ResponseEntity<Object> highestFee(@PathVariable String associateId,@RequestHeader HttpHeaders header);
    @DeleteMapping(value="/admission/deactivate/{courseId}")
    public ResponseEntity<Object> deactiveAdmission(@PathVariable String courseId,@RequestHeader HttpHeaders header);
    @PostMapping(value="/admission/feedback/{regNo}/{feedback}/{feedbackRating}")
    public ResponseEntity<Object> addFeedback(@PathVariable Long regNo, @PathVariable String feedback,@PathVariable float feedbackRating,@RequestHeader HttpHeaders header);
    @GetMapping(value = "/admission/viewFeedbackByCourseId/{courseId}")
	public ResponseEntity<Object> viewFeedbackByCourseId(@PathVariable String courseId,@RequestHeader HttpHeaders header);
    
}
