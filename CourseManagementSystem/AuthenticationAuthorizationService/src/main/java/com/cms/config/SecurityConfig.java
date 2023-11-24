package com.cms.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cms.jwt.EntryPointJwt;
import com.cms.jwt.TokenFilter;
import com.cms.service.UserDetailsImpl;
import com.cms.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig { 

	@Autowired
	TokenFilter filter;

	@Autowired
	UserDetailsServiceImpl uds;

	@Autowired
	EntryPointJwt entryPoint;
	
	@Autowired
	UserDetailsService userDetailsService;

	
	@Bean
    public SecurityFilterChain doFilter(HttpSecurity http) throws Exception {
		 http.authorizeHttpRequests().requestMatchers("/app/signin").permitAll()
		 .requestMatchers("/api/admin/**").permitAll().requestMatchers("/app/validateToken/**").permitAll().requestMatchers("/api/user/**").permitAll().and()
		.formLogin().and().csrf().disable().cors().disable().userDetailsService(uds).exceptionHandling()
		.authenticationEntryPoint(entryPoint)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    
  }
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
}
