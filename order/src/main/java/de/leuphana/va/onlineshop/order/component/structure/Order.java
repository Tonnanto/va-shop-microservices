package de.leuphana.va.onlineshop.order.component.structure;

import de.leuphana.va.onlineshop.article.component.structure.Article;

import java.util.Map;

public class Order {

	private int orderId;
	private int customerId;
	private Map<Integer, OrderPosition> orderPositions;

	public Order(int orderId) {
		this.orderId = orderId;
	}

	public Order() {
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getOrderId() {
		return orderId;
	}

	public Map<Integer, OrderPosition> getOrderPositions() {
		return orderPositions;
	}

	public void setOrderPosition(OrderPosition orderPosition) {
		orderPositions.put(orderPosition.getPositionId(), orderPosition);
	}

	public int getNumberOfArticles() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTotalPrice() {
		double totalPrice = 0.0;

		Article article;
		for (OrderPosition orderPosition : orderPositions.values()) {
			article = orderPosition.getArticle();

			totalPrice += orderPosition.getArticleQuantity() * article.getPrice();
		}

		return totalPrice;
	}

}