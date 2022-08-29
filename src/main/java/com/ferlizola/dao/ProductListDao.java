package com.ferlizola.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.ferlizola.product.Product;

@Component
public class ProductListDao {

	ArrayList<Product> prodList;
	
	public ProductListDao(){	
		prodList = new ArrayList<>();
		prodList.add(new Product("testing",(float) 12.00, "testing"));
	}

	public ArrayList<Product> findAll() {
		return prodList;
	}

	public void setProdList(ArrayList<Product> prodList) {
		this.prodList = prodList;
	}

	@Override
	public String toString() {
		return "ProductListBean [prodList=" + prodList + "]";
	} 
	
	
	
}
