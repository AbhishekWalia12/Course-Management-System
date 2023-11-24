package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.Associate;
import com.cms.model.Course;
import com.cms.proxy.AdmissionProxy;
import com.cms.proxy.AssociateProxy;
import com.cms.proxy.CourseProxy;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	CourseProxy courseProxy;
	@Autowired
	AssociateProxy associateProxy;
	@Autowired
	AdmissionProxy admissionProxy;

	@PostMapping("/api/admin/course/addCourse")
	public ResponseEntity<Object> addCourse(@RequestBody Course cObj, HttpServletRequest request) {
		ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = courseProxy.addCourse(cObj, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}

		return result;
	}

	@PutMapping(value = "/api/admin/course/update/{courseId}/{courseFees}")
	public ResponseEntity<Object> updateCourse(@PathVariable String courseId, @PathVariable("courseFees") Integer fees,
			HttpServletRequest request) {
		ResponseEntity<Object> result;
		String token = request.getHeader("Authorization");
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = courseProxy.updateCourse(courseId, fees, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;

	}

	@GetMapping(value = "/api/admin/course/viewFeedbackRating/{courseId}")
	public ResponseEntity<Object> findFeedbackRatingForCourseId(@PathVariable String courseId,
			HttpServletRequest request) {
		ResponseEntity<Object> result;
		String token = request.getHeader("Authorization");
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = courseProxy.findFeedbackRatingForCourseId(courseId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;

	}

	@PutMapping(value = "/api/admin/course/calculateAverageFeedback/{courseId}/{rating}")
	public ResponseEntity<Object> calculateAverageFeedbackAndUpdate(@PathVariable String courseId,
			@PathVariable float rating, HttpServletRequest request) {
		ResponseEntity<Object> result;
		String token = request.getHeader("Authorization");
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = courseProxy.calculateAverageFeedbackAndUpdate(courseId, rating, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;

	}

	@DeleteMapping(value = "/api/admin/course/deactivate/{courseId}")
	public ResponseEntity<Object> deactivateCourse(@PathVariable String courseId, HttpServletRequest request) {
		ResponseEntity<Object> result;
		String token = request.getHeader("Authorization");
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = courseProxy.deactivateCourse(courseId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;
	}

	@GetMapping(value = "/api/admin/course/viewAll")
	public ResponseEntity<Object> viewAllCourse(HttpServletRequest request) {
		ResponseEntity<Object> result;
		String token = request.getHeader("Authorization");
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", token);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = courseProxy.viewAll(header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.content());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;
	}

	@PostMapping(value = "/api/admin/associate/addAssociate")
	public ResponseEntity<Object> addAssociate(@RequestBody Associate cObj, HttpServletRequest request) {
		ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = associateProxy.addAssociate(cObj, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;

	}

	@GetMapping(value = "/api/admin/associate/viewAll")
	public ResponseEntity<Object> viewAllAssociate(HttpServletRequest request) {
		ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = associateProxy.viewAll(header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;

	}

	@PutMapping(value = "/api/admin/admission/calculateFees/{associateId}")
	public ResponseEntity<Object> calculateFees(@PathVariable String associateId, HttpServletRequest request) {
		ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = admissionProxy.calculateFees(associateId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;

	}

	@GetMapping(value = "/api/admin/admission/highestFee/{associateId}")
	public ResponseEntity<Object> highestFee(@PathVariable String associateId, HttpServletRequest request) {
		ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = admissionProxy.highestFee(associateId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;

	}

	@DeleteMapping(value = "/api/admin/admission/deactivate/{courseId}")
	public ResponseEntity<Object> deactiveAdmission(@PathVariable String courseId, HttpServletRequest request) {
		ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = admissionProxy.deactiveAdmission(courseId, header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;
	}

	@GetMapping(value = "/api/admin/admission/viewAll")
	public ResponseEntity<Object> viewAllAdmission(HttpServletRequest request) {
		ResponseEntity<Object> result;
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		if (isAdmin) {
			try {
				result = admissionProxy.viewAll(header);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
		}
		return result;

	}

}