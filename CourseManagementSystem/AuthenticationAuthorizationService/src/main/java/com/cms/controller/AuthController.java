package com.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.cms.jwt.JwtUtility;
import com.cms.request.LoginRequest;
import com.cms.response.JSONResponse;

import com.cms.service.UserDetailsServiceImpl;




@RestController
public class AuthController {
	
	@Autowired
	JwtUtility jwtTokenUtil;
	
	@Autowired
    UserDetailsServiceImpl userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;
	

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/app/signin")
	public ResponseEntity<?> validateUser(@RequestBody LoginRequest loginRequest){	
		try {
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

			String token = jwtTokenUtil.generateToken(authentication);
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority authority: userDetails.getAuthorities()) {
			roles.add(String.valueOf(authority));
			}
			JSONResponse jsonResponse = new JSONResponse(token, userDetails.getUsername(), roles);
			return ResponseEntity.ok(jsonResponse);
			}catch(Exception authExc){
			throw new RuntimeException(authExc.getMessage());
			}
		
	}
	@GetMapping(value="/app/validateToken/{authorization}")
	public boolean isValidToken(@PathVariable String authorization) {
		String token=authorization.substring(7);
		if(jwtTokenUtil.validateToken(token)) {
			return true;
		}
		return false;
	}
}
