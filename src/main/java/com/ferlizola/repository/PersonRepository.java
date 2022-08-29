package com.ferlizola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ferlizola.person.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	public Person findByEmail(String email);
}
