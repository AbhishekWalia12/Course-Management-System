package com.cms.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cms.service.UserDetailsServiceImpl;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {
	@Autowired
	JwtUtility jwtUtils;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
	String token = request.getHeader("Authorization"); 
	if (token != null && token.startsWith("Bearer ")) { 
	token = token.substring(7);
	    
	if (jwtUtils.validateToken(token)) {
	String username = jwtUtils.getUsernameFromToken(token);
	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	userDetailsService.loadUserByUsername(username), null, userDetailsService.loadUserByUsername(username).getAuthorities()); 
	SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
	else {
		
	}
	}
	filterChain.doFilter(request, response);
	}
	
}

