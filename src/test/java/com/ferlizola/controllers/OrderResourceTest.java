package com.ferlizola.controllers;

import java.util.Optional;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferlizola.order.Order;
import com.ferlizola.order.OrderNotFoundException;
import com.ferlizola.repository.OrderRepository;
import com.ferlizola.utils.OrderStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles(value="test")
@AutoConfigureMockMvc(addFilters = false)
public class OrderResourceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	OrderRepository orderRepo;
	
	Order RECORD_1 = new Order(100, OrderStatus.IN_PROGRESS, 200);
	Order RECORD_2 = new Order(1, OrderStatus.IN_PROGRESS, 100);
	
	@Test
	public void deleteOrderById_success() throws Exception {
	    Mockito.when(orderRepo.findById(RECORD_2.getOrderId())).thenReturn(Optional.of(RECORD_2));
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/order/1")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void deleteOrderById_failed() throws Exception {
	    Mockito.when(orderRepo.findById(1000)).thenReturn(null);
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/order/1")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().is4xxClientError())
	            .andExpect(result ->
                	assertTrue(result.getResolvedException() instanceof OrderNotFoundException));
	 }
	
	@Test
	public void updateOrderById_success() throws Exception {
		Order RECORD_UPD = new Order(100, OrderStatus.COMPLETED, 200);
		
		Mockito.when(orderRepo.findById(RECORD_1.getOrderId())).thenReturn(Optional.of(RECORD_1));
	    Mockito.when(orderRepo.save(RECORD_UPD)).thenReturn(RECORD_UPD);

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
	            .put("/order/100")
	            .contentType(MediaType.APPLICATION_JSON);
	    
	    mockMvc.perform(mockRequest)
	    		.andExpect(status().isOk());
	}
	
	@Test
	public void updateOrderById_failed() throws Exception {
	    Mockito.when(orderRepo.findById(1000)).thenReturn(null);
	    
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .put("/order/1")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().is4xxClientError())
	            .andExpect(result ->
                	assertTrue(result.getResolvedException() instanceof OrderNotFoundException));
	 }
	
	@Test
	public void getOrderById_success() throws Exception {
		Mockito.when(orderRepo.findById(RECORD_2.getOrderId())).thenReturn(Optional.of(RECORD_2));
		MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/order/1")
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		String expected = "{\"orderId\":1,\"orderStatus\":\"IN_PROGRESS\",\"totalAmount\":100}";
		
		JSONAssert.assertEquals(expected, res.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getOrderById_failed() throws Exception {
	    Mockito.when(orderRepo.findById(1000)).thenReturn(null);
	    
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/order/1")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().is4xxClientError())
	            .andExpect(result ->
                	assertTrue(result.getResolvedException() instanceof OrderNotFoundException));
	}
}
