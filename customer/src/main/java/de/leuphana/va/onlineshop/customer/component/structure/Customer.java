package de.leuphana.va.onlineshop.customer.component.structure;

import de.leuphana.va.onlineshop.order.component.structure.Order;

import java.util.HashMap;
import java.util.Map;

public class Customer {
	private static Integer lastGeneratedCustomerId;

	private Integer customerId;
	private String name;
	private String address;
	private Cart cart;
	private Map<Integer, Order> orders;

	public Customer() {}

	public Customer(Cart cart) {
		this.customerId = ++lastGeneratedCustomerId;
		this.cart = cart;
		orders = new HashMap<>();
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public Cart getCart() {
		return cart;
	}
	
	public void addOrder(Order order) {
		orders.put(order.getOrderId(), order);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}