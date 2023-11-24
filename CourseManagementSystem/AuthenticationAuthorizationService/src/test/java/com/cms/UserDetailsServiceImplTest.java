package com.cms;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cms.model.Login;
import com.cms.repository.UserRepository;
import com.cms.service.UserDetailsServiceImpl;

@SpringBootTest(classes = { UserDetailsServiceImplTest.class })
public class UserDetailsServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	// Test whether loadByUserName returns UserDetails for valid credentials
	@Test
	public void test118LoadByUserName() {
		Login user = new Login();
		user.setUsername("testUser");
		user.setPassword("testPassword");
		user.setRole("ROLE_USER");

		when(userRepository.findByUsername("testUser")).thenReturn(user);

		UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

		assertEquals("testUser", userDetails.getUsername());
		assertEquals("testPassword", userDetails.getPassword());

	}

	// Test whether loadByUserName throws Exception for invalid user
	@Test
	public void test118LoadByUserNameForInvalidUser() {
		String invalidUsername = "invalidUser";
	    when(userRepository.findByUsername(invalidUsername)).thenReturn(null);

	    
	    assertThrows(UsernameNotFoundException.class, () -> {
	        userDetailsService.loadUserByUsername(invalidUsername);
	    });

	}

}
