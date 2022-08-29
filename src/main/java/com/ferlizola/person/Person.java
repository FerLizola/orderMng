package com.ferlizola.person;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ferlizola.order.Order;

@Entity
public class Person {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int personId;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String pwd;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="person")
	@JsonIgnore
	private List<Order> order;

	protected Person() {}
	public Person(String firstName, String lastName, String address, String email, String pwd) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.pwd = pwd;
	}

	public int getPersonId() {
		return personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setOrder(Order order) {
		this.order.add(order);
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "Person [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + "]";
	}
	
	
}
