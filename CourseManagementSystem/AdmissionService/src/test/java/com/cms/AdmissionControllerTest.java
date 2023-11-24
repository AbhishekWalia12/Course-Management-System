package com.cms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.AdmissionController;
import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Course;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.service.AdmissionServiceImpl;



//Write mockito tests for the endpoints in the AdmissionController
@ComponentScan(basePackages = "com.cms.*")
@AutoConfigureMockMvc
@ContextConfiguration(classes = AdmissionServiceApplication.class)
@SpringBootTest
public class AdmissionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AdmissionServiceImpl admissionService;

	@Mock
	private AuthenticationAuthorizationProxy authProxy;

	@InjectMocks
	private AdmissionController admissionController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(admissionController).build();
		
		
	}
	

	//Test whether the endpoint /highestFee/{associateId} is successful
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourse() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		List<String> s1=new ArrayList<>();
		s1.add("201");
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(admissionService.highestFeeForTheRegisteredCourse("101")).thenReturn(s1);
		 mockMvc.perform(get("/admission/highestFee/{associateId}", "101").header("Authorization", token))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$[0]", is(s1.get(0))));
	}
	
	//Test whether the end point /viewFeedbackByCourseId/{courseId} is successful
	@Test
	public void test102RestApiCallForViewFeedbackByCourseId() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		List<String> s1=new ArrayList<>();
		s1.add("Good");
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(admissionService.viewFeedbackByCourseId("201")).thenReturn(s1);
		mockMvc.perform(get("/admission/viewFeedbackByCourseId/{courseId}", "201").header("Authorization", token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]", is(s1.get(0))));
		
		
	}
	@Test
	public void  test103RestApiCallForDeactivateAdmission() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(admissionService.deactivateAdmission("201")).thenReturn(true);
		
		mockMvc.perform(delete("/admission/deactivate/{courseId}", "201").header("Authorization", token))
		.andExpect(status().isOk());
        
		
	}
	
    // Test whether the end point /calculateFees/{associateId} is successful
	@Test
	public void test107RestApiCallForCalculateFees() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(admissionService.calculateFees("101")).thenReturn(2000);
		mockMvc.perform(put("/admission/calculateFees/{associateId}", "101").header("Authorization", token))
        .andExpect(status().isOk())
        .andDo(result -> {
            String responseBody = result.getResponse().getContentAsString();
            int actualFees = Integer.parseInt(responseBody);
            assertEquals(2000, actualFees);
        });
		
	}
	
	// Test whether the end point /highestFee/{associateId} is successful for invalid id
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourseForInvalidId() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		List<String> s1=new ArrayList<>();
		s1.add("201");
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(admissionService.highestFeeForTheRegisteredCourse("101")).thenThrow(new AdmissionInvalidException("AssociateId does not exists"));
		 mockMvc.perform(get("/admission/highestFee/{associateId}", "101").header("Authorization", token))
         .andExpect(status().isNotFound())
         .andExpect(content().string("AssociateId does not exists"));
        
		
	}
	
	// Test whether the end point /highestFee/{associateId} is successful for invalid token
	@Test
	public void test101RestApiCallForHighestFeeForTheRegisteredCourseForInvalidToken() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		
		when(authProxy.isValidToken(token)).thenReturn(false);
		
		 mockMvc.perform(get("/admission/highestFee/{associateId}", "201").header("Authorization", token))
         .andExpect(status().isUnauthorized());
		
	}
	
	
	//Test whether the end point /viewFeedbackByCourseId/{courseId} is successful for invalid id
	@Test
	public void test102RestApiCallForViewFeedbackByCourseIdForInvalidId() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		List<String> s1=new ArrayList<>();
		s1.add("Good");
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(admissionService.viewFeedbackByCourseId("201")).thenThrow(new AdmissionInvalidException("Invalid Course Id"));
		mockMvc.perform(get("/admission/viewFeedbackByCourseId/{courseId}", "201").header("Authorization", token))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Invalid Course Id"));
      
		
	}
	
	//Test whether the end point /viewFeedbackByCourseId/{courseId} is successful for invalid token
	@Test
	public void test102RestApiCallForViewFeedbackByCourseIdForInvalidToken() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		
		when(authProxy.isValidToken(token)).thenReturn(false);
		
		mockMvc.perform(get("/admission/viewFeedbackByCourseId/{courseId}", "201").header("Authorization", token))
        .andExpect(status().isUnauthorized());
		
	}
	
	
	//Test whether the end point /deactivate/{courseId} is successful for invalid id
	@Test
	public void test103RestApiCallForDeactivateAdmissionForInvalidId() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(admissionService.deactivateAdmission("201")).thenThrow(new AdmissionInvalidException("CourseId does not exists"));
		
		mockMvc.perform(delete("/admission/deactivate/{courseId}", "201").header("Authorization", token))
		.andExpect(status().isNotFound())
        .andExpect(content().string("CourseId does not exists"));
		
		
	}
	
	//Test whether the end point /deactivate/{courseId} is successful for invalid token
	@Test
	public void test103RestApiCallForDeactivateAdmissionForInvalidToken() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		
		when(authProxy.isValidToken(token)).thenReturn(false);
		
		mockMvc.perform(delete("/admission/deactivate/{courseId}", "101").header("Authorization", token))
        .andExpect(status().isUnauthorized());

	}
	
	
	//Test whether the end point /calculateFees/{associateId} is successful for invalid id
	@Test
	public void test107RestApiCallForCalculateFeesForInvalidId() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(admissionService.calculateFees("101")).thenThrow(new AdmissionInvalidException("AssociateId does not exists"));
		mockMvc.perform(put("/admission/calculateFees/{associateId}", "101").header("Authorization", token))
        .andExpect(status().isNotFound())
        .andExpect(content().string("AssociateId does not exists"));
    
        
		
	}
	
	//Test whether the end point /calculateFees/{associateId} is successful for invalid token
	@Test
	public void test107RestApiCallForCalculateFeesForInvalidToken() throws Exception {
		String token = "sample_token";
		long regId= 340;
		Admission adminssion=new Admission();
		adminssion.setRegistrationId(regId);
		adminssion.setAssociateId("101");
		adminssion.setCourseId("201");
		adminssion.setFees(2000);
		adminssion.setFeedback("Good");
		
		when(authProxy.isValidToken(token)).thenReturn(false);
		
		mockMvc.perform(put("/admission/calculateFees/{associateId}", "101").header("Authorization", token))
        .andExpect(status().isUnauthorized());
	}

}
