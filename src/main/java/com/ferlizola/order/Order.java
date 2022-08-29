package com.ferlizola.order;

import java.util.List;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

import com.ferlizola.person.Person;
import com.ferlizola.utils.OrderStatus;

@Entity
@Table(name="Orders")
public class Order {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int orderId;
	
	@NotNull
	private String orderStatus;
	
	@Min(value=0, message="The amount should be greater than 0")
	private float totalAmount;
	private Date orderDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Person person;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="order")
	private List<OrderItem> orderItems;
	
	protected Order() {}

	public Order(String orderStatus, float totalAmount, Date date) {
		super();
		this.orderStatus = orderStatus;
		this.totalAmount = totalAmount;
		this.orderDate = date;
	}

	public Order(OrderStatus inProgress, float totalAmount) {
		this.orderStatus = inProgress.toString();
		this.totalAmount = totalAmount;
	
	}

	public Order(int orderId, OrderStatus inProgress, float totalAmount) {
		// TODO Auto-generated constructor stub
		this.orderId = orderId;
		this.orderStatus = inProgress.toString();
		this.totalAmount = totalAmount;
	}

	public int getOrderId() {
		return orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setProd(OrderItem prod) {
		this.orderItems.add(prod);
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public Person getPerson() {
		return person;
	}

	/*public void setOrderId(int orderId) {
		this.orderId = orderId;
	}*/

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderStatus=" + orderStatus + ", totalAmount=" + totalAmount
				+ ", person=" + person + "]";
	}
	
	
}
