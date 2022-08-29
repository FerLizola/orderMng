package com.ferlizola.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int productId;
	private String prodName;
	private float prodPrice;
	private String prodDescr;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//private OrderItem orderItem;
	protected Product() {}
	public Product(String prodName, float prodPrice, String prodDescr) {
		super();
		this.prodName = prodName;
		this.prodPrice = prodPrice;
		this.prodDescr = prodDescr;
	}

	public long getProdID() {
		return productId;
	}
	
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public float getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(float prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getProdDescr() {
		return prodDescr;
	}

	public void setProdDescr(String prodDescr) {
		this.prodDescr = prodDescr;
	}

	/*public void setOrder(OrderItem order) {
		this.orderItem = order;
	}*/

	
	@Override
	public String toString() {
		return "Product [prodID=" + productId + ", prodName=" + prodName + ", prodPrice=" + prodPrice + ", prodDescr="
				+ prodDescr + "]";
	}

	public void setProdID(int prodID) {
		this.productId = prodID;
	}
	
	
}
