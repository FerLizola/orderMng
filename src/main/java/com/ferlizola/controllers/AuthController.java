package com.ferlizola.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ferlizola.jwtconfig.JwtRequest;
import com.ferlizola.jwtconfig.JwtResponse;
import com.ferlizola.jwtconfig.JwtTokenUtil;
import com.ferlizola.jwtconfig.JwtUserDetailsService;

@RestController
@CrossOrigin
public class AuthController {
	
	Logger logger = LoggerFactory.getLogger(AuthController.class);

	private AuthenticationManager authenticationManager;
	
	@Autowired
//	private AuthenticationManager authenticationManager;
	public void setAuthenticationManager(@Lazy AuthenticationManager auth) {
		authenticationManager = auth;
	}

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) 
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			logger.info("USER :: " + username + " :: " + password);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			logger.error("" + e.getMessage());
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
