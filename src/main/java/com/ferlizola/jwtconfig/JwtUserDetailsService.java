package com.ferlizola.jwtconfig;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ferlizola.person.Person;
import com.ferlizola.repository.PersonRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private PersonRepository person;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			Person user = person.findByEmail(username);

			return new User(username, user.getPwd(), new ArrayList<>());
		} catch (Exception ex) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

	}
}
