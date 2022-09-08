package com.ferlizola.controllers;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferlizola.product.Product;
import com.ferlizola.repository.ProductRepository;

//@WebMvcTest(ProductsResource.class)
//@WithMockUser
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles(value="test")
@AutoConfigureMockMvc(addFilters = false)
public class ProductsResourceTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	ProductRepository prodRepo;
	
	Product PROD_RECORD_1 = new Product( "Test prod", 1, "Testing products");
	Product PROD_RECORD_2 = new Product( "Test prod 2", 2, "Testing products v2");
	
	@Test
	public void getAllProd_success() throws Exception{
		List<Product> products = new ArrayList<>();
		products.add(PROD_RECORD_1);
		products.add(PROD_RECORD_2);
		
		Mockito.when(prodRepo.findAll()).thenReturn(products);
		
		String expected = "[{\"prodName\":\"Test prod\",\"prodPrice\":1.0,\"prodDescr\":\"Testing products\",\"prodID\":0},{\"prodName\":\"Test prod 2\",\"prodPrice\":2.0,\"prodDescr\":\"Testing products v2\",\"prodID\":0}]";
		
		MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/products")
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		System.out.println(res.toString());
		
		/*mockMvc.perform(MockMvcRequestBuilders.get("/products")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.size()", is(products.size())));*/
					//.andExpect(jsonPath("$[1].prodName", is("Test prod")));
		JSONAssert.assertEquals(expected, res.getResponse().getContentAsString(), false);
	}
}
