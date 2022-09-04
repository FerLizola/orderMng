package com.ferlizola.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.ferlizola.person.Person;
import com.ferlizola.repository.PersonRepository;

@RestController
@CrossOrigin
public class PersonController {

	@Autowired
	PersonRepository personRepo;
	
	@PostMapping(path="/personId")
	public int getUserID(@RequestBody JsonNode payload) {
		String email = payload.get("username").toString().replace("\"", "");
		System.out.println("USERNAME ::: " + email);
		Optional<Person> person = personRepo.findByEmail(email);
		if(!person.isPresent())
			throw new UsernameNotFoundException("User not found with username: " + email);
		return person.get().getPersonId();
	}
}
