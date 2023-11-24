package com.cms;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.AuthController;
import com.cms.jwt.JwtUtility;
import com.cms.request.LoginRequest;
import com.cms.service.UserDetailsImpl;
import com.cms.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@ComponentScan(basePackages = "com.cms.*")
@AutoConfigureMockMvc
@ContextConfiguration(classes = AuthenticationAuthorizationServiceApplication.class)
@SpringBootTest
public class AuthControllerTest {
	@InjectMocks
    private AuthController signInController;

    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private UserDetailsServiceImpl userDetailsService;

    

    @Mock
    private JwtUtility jwtTokenUtil;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
    	MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(signInController).build();
    }
	
	
	@Test
	public void test202RestApiCallForTokenGeneration() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setUsername("testUser");
	    loginRequest.setPassword("testPassword");

	   
	    Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
	    when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()))).thenReturn(authentication);

	    
	    UserDetails userDetails = new UserDetailsImpl("testUser", "testPassword", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
	    when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

	    
	    String token = "sampleToken";
	    when(jwtTokenUtil.generateToken(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()))).thenReturn(token);
	    ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(loginRequest);

	   
	    mockMvc.perform(post("/app/signin")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.accessToken").value(token))
	            .andExpect(jsonPath("$.username").value(userDetails.getUsername()))
	            .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));

	    // Verify the authentication manager and user details service were called
	    

	} 
	
	//Test whether the endpoint /app/validateToken/{authorization} is successful
	@Test
	public void test203RestApiCallForValidatingToken() throws Exception{
		String token = "sampleToken";

	    
	    when(jwtTokenUtil.validateToken(token)).thenReturn(true);

	    mockMvc.perform(get("/app/validateToken/{authorization}", "Bearer " + token))
	            .andExpect(status().isOk())
	            .andExpect(content().string("true"));

	    
	    
			
	} 
	
	

}

