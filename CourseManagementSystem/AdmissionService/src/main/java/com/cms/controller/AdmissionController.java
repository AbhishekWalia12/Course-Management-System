package com.cms.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;

import com.cms.config.RabbitMQConfig;
import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Associate;
import com.cms.model.Course;
import com.cms.payment.Order;
import com.cms.payment.PaypalService;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.proxy.CourseProxy;
import com.cms.service.AdmissionServiceImpl;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.rabbitmq.client.Channel;

import com.rabbitmq.client.DeliverCallback;

import feign.FeignException;


@RestController
public class AdmissionController {
	@Autowired
	AdmissionServiceImpl admissionServic;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CourseProxy cProxy;

	@Autowired
	AuthenticationAuthorizationProxy authProxy;

	@Autowired
	RabbitMQConfig rabbitMq;

	@Autowired
	Queue queue;

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	PaypalService service;
	
	

	public static final String SUCCESS_URL = "admission/success";
	public static final String CANCEL_URL = "admission/cancel";

	@PostMapping(value = "/admission/register/{associateId}/{courseId}")
	public ResponseEntity<Object> registerAssociateForCourse(@PathVariable String associateId,
			@PathVariable String courseId, @RequestHeader("Authorization") String token)
			throws IOException, TimeoutException {

		ResponseEntity<Object> result;
		if (authProxy.isValidToken(token)) {
			try {
				HttpHeaders header = new HttpHeaders();
				header.set("Authorization", token);
				HttpEntity request = new HttpEntity(header);
				ResponseEntity<Associate> associateResponse = restTemplate.exchange(
						"http://localhost:9092/associate/viewByAssociateId/" + associateId, HttpMethod.GET, request,
						Associate.class, associateId);
				ResponseEntity<Course> courseResponse = restTemplate.exchange(
						"http://localhost:9091/course/viewByCourseId/" + courseId, HttpMethod.GET, request,
						Course.class, courseId);
				Admission admission = new Admission();
				admission.setCourseId(courseResponse.getBody().getCourseId());
				admission.setAssociateId(associateResponse.getBody().getAssociateId());
				admission.setFees(courseResponse.getBody().getFees());
				admission = admissionServic.registerAssociateForCourse(admission);
				CachingConnectionFactory factory = rabbitMq.connectionFactory();
				Connection connection = factory.createConnection();
				Channel channel = connection.createChannel(false);
				channel.queueDeclare(queue.getName(), true, false, false, null);
				String message = "\"registrationNumber\": \"" + admission.getRegistrationId() + "\","
						+ "\"courseId\": \"" + courseId + "\"," + "\"associateId\": \"" + associateId + "\","
						+ "\"registrationDate\": \"" + java.time.LocalDate.now() + "\"";
				byte[] messageBytes = message.getBytes();
				channel.basicPublish("", queue.getName(), null, messageBytes);
				DeliverCallback deliverCallback = (consumerTag, delivery) -> {
					String message1 = new String(delivery.getBody(), "UTF-8");
					System.out.println("Received message: " + message);
					SimpleMailMessage message2 = new SimpleMailMessage();
					message2.setFrom("abhishekwalia12@gmail.com");
					message2.setTo(associateResponse.getBody().getAssociateEmailId());
					message2.setSubject("Confirmation");
					message2.setText(message1);
					System.out.println(true);
					mailSender.send(message2);
					
				};
				channel.basicConsume(queue.getName(), true , deliverCallback, consumerTag -> {
				});
				channel.close();
				connection.close();
				result = ResponseEntity.ok(admission);
			} catch (FeignException e) {
				result = ResponseEntity.status(e.status()).body(e.contentUTF8());
			} catch (AdmissionInvalidException e) {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return result;
	}

	@PutMapping(value = "/admission/calculateFees/{associateId}")
	public ResponseEntity<Object> calculateFees(@PathVariable String associateId,@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
		try {
			int fees = admissionServic.calculateFees(associateId);
			result = ResponseEntity.ok(fees);
		} catch (AdmissionInvalidException ex) {
			result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} }else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

	@PostMapping(value = "/admission/feedback/{regNo}/{feedback}/{feedbackRating}")
	@Retryable(retryFor = RuntimeException.class, backoff = @Backoff(delay = 100, maxDelay = 101), maxAttempts = 1)
	public ResponseEntity<Object> addFeedback(@PathVariable Long regNo, @PathVariable String feedback,
			@PathVariable float feedbackRating,@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
			HttpHeaders header = new HttpHeaders();
			header.set("Authorization", token);
		try {
			Admission newFeedback = admissionServic.addFeedback(regNo, feedback, feedbackRating);
			String courseId = newFeedback.getCourseId();
			cProxy.calculateAverageFeedbackAndUpdate(courseId, feedbackRating,header);
			result = ResponseEntity.ok(newFeedback);

		} catch (AdmissionInvalidException ex) {
			result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (RuntimeException ex) {
			result = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
		}}else{
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} 
		return result;
	}

	@GetMapping(value = "/admission/highestFee/{associateId}")
	public ResponseEntity<Object> highestFee(@PathVariable String associateId,@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
		try {
			List<String> courseList = admissionServic.highestFeeForTheRegisteredCourse(associateId);
			result = ResponseEntity.ok(courseList);
		} catch (AdmissionInvalidException ex) {
			result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} } else{
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return result;
	}

	@GetMapping(value = "/admission/viewFeedbackByCourseId/{courseId}")
	public ResponseEntity<Object> viewFeedbackByCourseId(@PathVariable String courseId,@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
		try {
			List<String> feedback = admissionServic.viewFeedbackByCourseId(courseId);
			result = ResponseEntity.ok(feedback);
		} catch (AdmissionInvalidException ex) {
			result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} }else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

	@DeleteMapping(value = "/admission/deactivate/{courseId}")
	public ResponseEntity<Object> deactiveAdmission(@PathVariable String courseId,@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
		try {
			Boolean ans = admissionServic.deactivateAdmission(courseId);
			result = ResponseEntity.ok(ans);
		} catch (AdmissionInvalidException e) {
			result = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} }else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}
	
	@PostMapping("/admission/makePayment/{registartionId}")
	@CrossOrigin(origins = "http://localhost:3000")
	public String makePayment(@PathVariable int registartionId){
		String result="redirect:/";
		try {
		    
			boolean reg=admissionServic.makePayment(registartionId);
			if(reg) {
				Order order=new Order();
			order.setCurrency("USD");
			order.setDescription("Buying course");
			order.setIntent("sale");
			order.setMethod("paypal");
			List<Admission> allAdmission=admissionServic.viewAll();
			for(Admission a:allAdmission) {
				if(a.getRegistrationId()==registartionId) {
					order.setPrice(a.getFees());
				}
			}
			Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
					order.getIntent(), order.getDescription(), "http://localhost:9093/" + CANCEL_URL,
					"http://localhost:9093/" + SUCCESS_URL);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					result="redirect:"+link.getHref();
				}
			}
			}
			
		} catch (PayPalRESTException e) {		
			e.printStackTrace();
		}catch(AdmissionInvalidException e) {
			result=e.getMessage();
		}
		return result;
	}
	 @GetMapping(value = CANCEL_URL)
	    public String cancelPay() {
	        return "cancel";
	    }

	    @GetMapping(value = SUCCESS_URL)
	    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
	    	String result="redirect:/";
	        try {
	            Payment payment = service.executePayment(paymentId, payerId);
	            System.out.println(payment.toJSON());
	            if (payment.getState().equals("approved")) {
	                result="success";
	            }
	        } catch (PayPalRESTException e) {
	         System.out.println(e.getMessage());
	        }
	        return result;
	    }

	@GetMapping(value = "/admission/viewAll")
	public ResponseEntity<Object> viewAll(@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if (authProxy.isValidToken(token)) {
			List<Admission> allAdmission = admissionServic.viewAll();
			result = ResponseEntity.ok(allAdmission);

		} else {
			result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return result;
	}

}
