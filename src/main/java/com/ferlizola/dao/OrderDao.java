package com.ferlizola.dao;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import com.ferlizola.order.Order;
import com.ferlizola.utils.OrderStatus;

@Component
public class OrderDao {
	
ArrayList<Order> orderList;
	
	public OrderDao(){	
		orderList = new ArrayList<>();
		orderList.add(new Order(12,OrderStatus.IN_PROGRESS,(float) 12.00));
	}

	public ArrayList<Order> findAll() {
		return orderList;
	}
	
	public Order findById(long orderId) {
		for(Order order: orderList) {
			if(order.getOrderId()==orderId) {
				return order;
			}
		}
		return null;
	}
	
	public Order deleteById(long orderId) {
		Iterator<Order> iterator = orderList.iterator();
		while(iterator.hasNext()) {
			Order order = iterator.next();
			if(order.getOrderId()==orderId) {
				iterator.remove();
				return order;
			}
		}
		return null;
	}
	
	public Order save(Order order) {
		orderList.add(order);
		return order;
	}

	public void setProdList(ArrayList<Order> orderList) {
		this.orderList = orderList;
	}

}
