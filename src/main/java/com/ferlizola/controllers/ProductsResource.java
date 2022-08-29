package com.ferlizola.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ferlizola.product.Product;
import com.ferlizola.repository.ProductRepository;

@CrossOrigin
@RestController
public class ProductsResource {
	
	@Autowired
	private ProductRepository prodRepository;
	
	@RequestMapping(method=RequestMethod.GET, path="/products")
	public List<Product> showProducts() {
		return prodRepository.findAll();
	}
	
}
