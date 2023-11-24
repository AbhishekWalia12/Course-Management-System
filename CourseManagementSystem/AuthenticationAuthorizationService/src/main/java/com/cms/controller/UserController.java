package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.proxy.AdmissionProxy;
import com.cms.proxy.AssociateProxy;
import com.cms.proxy.CourseProxy;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController{
	
	@Autowired
	CourseProxy courseProxy;
	@Autowired
	AssociateProxy associateProxy;
	@Autowired
	AdmissionProxy admissionProxy;
	
   @GetMapping(value="/api/user/course/viewByCourseId/{courseId}")
   public ResponseEntity<Object> viewByCourseId(@PathVariable String courseId,HttpServletRequest request){
	   ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isUser = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
		if (isUser) {
			try {
				result =courseProxy.viewByCourseId(courseId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;
   }
   @PutMapping(value="/api/user/associate/updateAssociate/{associateId}/{associateAddr}")
   public ResponseEntity<Object> updateAssociate(@PathVariable String associateId,@PathVariable("associateAddr") String associateAddress,HttpServletRequest request){
	   ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isUser = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
		if (isUser) {
			try {
				result =associateProxy.updateAssociate(associateId, associateAddress, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;
   }
   @GetMapping(value="/api/user/associate/viewByAssociateId/{associateId}")
   public ResponseEntity<Object> viewByAssociateId(@PathVariable String associateId,HttpServletRequest request){  
	   ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isUser = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
		if (isUser) {
			try {
				result =associateProxy.viewByAssociateId(associateId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;
   }
   @PostMapping(value="/api/user/admission/register/{associateId}/{courseId}")
	public ResponseEntity<Object> registerAssociateForCourse(@PathVariable String associateId,@PathVariable String courseId,HttpServletRequest request){
	   ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isUser = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
		if (isUser) {
			try {
				result =admissionProxy.registerAssociateForCourse(associateId, courseId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;   
   }
   @PostMapping(value="/api/user/admission/feedback/{regNo}/{feedback}/{feedbackRating}")
   public ResponseEntity<Object> addFeedback(@PathVariable Long regNo, @PathVariable String feedback,
			@PathVariable float feedbackRating,HttpServletRequest request) {
	   ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isUser = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
		if (isUser) {
			try {
				result =admissionProxy.addFeedback(regNo, feedback, feedbackRating, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;  
   }
   @GetMapping(value = "/api/user/admission/viewFeedbackByCourseId/{courseId}")
	public ResponseEntity<Object> viewFeedbackByCourseId(@PathVariable String courseId,HttpServletRequest request){
	   ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isUser = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
		if (isUser) {
			try {
				result =admissionProxy.viewFeedbackByCourseId(courseId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result; 
   }
    
}