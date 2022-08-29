package com.ferlizola.order;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import com.ferlizola.product.Product;

//import com.ferlizola.dao.Product;

@Entity
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int itemId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Product prod;
	
	@Min(value=1, message="The quantity of products should be atleast 1")
	private int quantity;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Order order;

	public OrderItem() {}
	public OrderItem(int quantity) {
		super();
		this.quantity = quantity;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public Product getProd() {
		return prod;
	}

	public void setProd(Product prod) {
		this.prod = prod;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "OrderItem [itemId=" + itemId /*+ ", prod=" + prod + ", quantity="*/ + quantity + "]";
	}
	
	
}
