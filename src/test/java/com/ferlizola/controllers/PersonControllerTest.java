package com.ferlizola.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ferlizola.person.Person;
import com.ferlizola.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles(value="test")
@AutoConfigureMockMvc(addFilters = false)
public class PersonControllerTest {
	
	@Autowired
	PersonRepository personRepo;
	
	@Autowired
	MockMvc mockMvc;
	
	Person RECORD_1 = new Person("Fernando", "Lizola", "Test Address", "fer_lizola@hotmail.com", "test123");
	
	@Test
	public void getUserByEmail_success() throws Exception {
		RECORD_1.setPersonId(1);
	    Mockito.when(personRepo.findByEmail(RECORD_1.getEmail())).thenReturn(java.util.Optional.of(RECORD_1));
	    String jsonReq = "{\"username\":\"fer_lizola@hotmail.com\"}";

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
	            .post("/personId")
	            .contentType(MediaType.APPLICATION_JSON)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.content(jsonReq);
	    MvcResult res = mockMvc.perform(mockRequest).andReturn();
	    
	    String expected = "{\"personId\":1, \"firstName\":\"Fernando\", \"lastName\":\"Lizola\", \"address\":\"Test Address\", \"email\":\"fer_lizola@hotmail.com\", \"pwd\":\"test123\"}";
	    
	    JSONAssert.assertEquals(expected, res.getResponse().getContentAsString(), false);
	}

}
