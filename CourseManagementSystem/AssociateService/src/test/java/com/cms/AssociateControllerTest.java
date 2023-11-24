package com.cms;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.AssociateController;
import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.service.AssociateServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


//Write mockito tests for the endpoints in the AssociateController
@ComponentScan(basePackages = "com.cms.*")
@AutoConfigureMockMvc
@ContextConfiguration(classes =AssociateServiceApplication.class)
@SpringBootTest
public class AssociateControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AssociateServiceImpl asssociateImpl;

	@Mock
	private AuthenticationAuthorizationProxy authProxy;

	@InjectMocks
	private  AssociateController associateController;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(associateController).build();
	}
	   
	
	 
	//Test whether the endpoint /viewByAssociateId/{associateId} is successful
	@Test
	public void test115RestApiCallForViewByAssociateId() throws Exception {
		String token = "sample_token";
		String associateId="201";
		Associate associate=new Associate();
		associate.setAssociateId(associateId);
		associate.setAssociateEmailId("sannusingh22@gmail.com");

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(asssociateImpl.viewByAssociateId(associateId)).thenReturn(associate);
		mockMvc.perform(get("/associate/viewByAssociateId/{associateId}", associateId).header("Authorization", token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.associateId").value("201"))
				.andExpect(jsonPath("$.associateEmailId").value("sannusingh22@gmail.com"));
		
	}
	//Test whether the end point /updateAssociate/{associateId}/{associateAddress} is successful
	@Test
	public void test116RestApiCallForUpdateAssociate() throws Exception {
		String token = "sample_token";
		String associateId="201";
		Associate associate=new Associate();
		associate.setAssociateId(associateId);
		associate.setAssociateEmailId("sannusingh22@gmail.com");
       associate.setAssociateAddress("Hyderabad");
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(asssociateImpl.updateAssociate(associateId, "Hyderabad")).thenReturn(associate);
		mockMvc.perform(put("/associate/updateAssociate/{associateId}/{associateAddress}", associateId,"Hyderabad").header("Authorization", token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.associateId").value("201"))
				.andExpect(jsonPath("$.associateEmailId").value("sannusingh22@gmail.com"))
				.andExpect(jsonPath("$.associateAddress").value("Hyderabad"));
		
	}
	
	//Test whether the endpoint /addAssociate is successful
	@Test
	public void test117RestApiCallForAddAssociate() throws Exception {
		String token = "sample_token";
		String associateId="201";
		Associate associate=new Associate();
		associate.setAssociateId(associateId);
		associate.setAssociateEmailId("sannusingh22@gmail.com");
       associate.setAssociateAddress("Hyderabad");
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(asssociateImpl.addAssociate(associate)).thenReturn(associate);
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(associate);
		mockMvc.perform(post("/associate/addAssociate").header("Authorization", token).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
	}
	
	//Test whether the endpoint /viewByAssociateId/{associateId} is successful for invalid token
	@Test
	public void test115RestApiCallForViewByAssociateIdForInvalidToken() throws Exception {
		String token = "sample_token";
		String associateId="201";
		Associate associate=new Associate();
		associate.setAssociateId(associateId);
		associate.setAssociateEmailId("sannusingh22@gmail.com");

		when(authProxy.isValidToken(token)).thenReturn(false);
		
		mockMvc.perform(get("/associate/viewByAssociateId/{associateId}", associateId).header("Authorization", token))
				.andExpect(status().isUnauthorized());
		
	}
	
	//Test whether the endpoint /viewByAssociateId/{associateId} is successful for invalid id
	@Test
	public void test115RestApiCallForViewByAssociateIdForInvalidId() throws Exception {
		String token = "sample_token";
		String associateId="201";
		Associate associate=new Associate();
		associate.setAssociateId(associateId);
		associate.setAssociateEmailId("sannusingh22@gmail.com");

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(asssociateImpl.viewByAssociateId(associateId)).thenThrow(new AssociateInvalidException("Invalid Associate Id"));
		mockMvc.perform(get("/associate/viewByAssociateId/{associateId}", associateId).header("Authorization", token))
				.andExpect(status().isNotFound()).andExpect(content().string("Invalid Associate Id"));

	}
	
	//Test whether the end point /updateAssociate/{associateId}/{associateAddress} is successful for invalid token
	@Test
	public void test116RestApiCallForUpdateAssociateForInvalidToken() throws Exception {
		String token = "sample_token";
		String associateId="201";
		Associate associate=new Associate();
		associate.setAssociateId(associateId);
		associate.setAssociateEmailId("sannusingh22@gmail.com");
       associate.setAssociateAddress("Hyderabad");
		when(authProxy.isValidToken(token)).thenReturn(false);
		
		mockMvc.perform(put("/associate/updateAssociate/{associateId}/{associateAddress}", associateId,"Hyderabad").header("Authorization", token))
				.andExpect(status().isUnauthorized());
		
	}
	
	//Test whether the end point /updateAssociate/{associateId}/{associateAddress} is successful for invalid id
	@Test
	public void test116RestApiCallForUpdateAssociateForInvalidId() throws Exception {
		String token = "sample_token";
		String associateId="201";
		Associate associate=new Associate();
		associate.setAssociateId(associateId);
		associate.setAssociateEmailId("sannusingh22@gmail.com");
       associate.setAssociateAddress("Hyderabad");
		when(authProxy.isValidToken(token)).thenReturn(true);
		when(asssociateImpl.updateAssociate(associateId, "Hyderabad")).thenThrow(new AssociateInvalidException("AssociateId does not exists"));
		mockMvc.perform(put("/associate/updateAssociate/{associateId}/{associateAddress}", associateId,"Hyderabad").header("Authorization", token))
				.andExpect(status().isNotFound()).andExpect(content().string("AssociateId does not exists"));
		
	}
	@Test
	public void test117RestApiCallForAddAssociateForInvalidToken() throws Exception {
		String token = "sample_token";
		String associateId="201";
		Associate associate=new Associate();
		associate.setAssociateId(associateId);
		associate.setAssociateEmailId("sannusingh22@gmail.com");
       associate.setAssociateAddress("Hyderabad");
		when(authProxy.isValidToken(token)).thenReturn(false);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(associate);
		mockMvc.perform(post("/associate/addAssociate").header("Authorization", token).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
		
	}
	

}