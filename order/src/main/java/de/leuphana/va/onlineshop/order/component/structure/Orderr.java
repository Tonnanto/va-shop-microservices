package de.leuphana.va.onlineshop.order.component.structure;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Orderr {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderId;
	private int customerId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<OrderPosition> orderPositions;

	public Orderr() {
		orderPositions = new HashSet<>();
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

	public Set<OrderPosition> getOrderPositions() {
		return orderPositions;
	}

	public void addOrderPosition(OrderPosition orderPosition) {
		orderPositions.add(orderPosition);
	}

	public int getNumberOfArticles() {
		return orderPositions.size();
	}

//	public double getTotalPrice() {
//		double totalPrice = 0.0;
//
//		Article article;
//		for (OrderPosition orderPosition : orderPositions.values()) {
//			article = orderPosition.getArticle();
//
//			totalPrice += orderPosition.getArticleQuantity() * article.getPrice();
//		}
//
//		return totalPrice;
//	}

}