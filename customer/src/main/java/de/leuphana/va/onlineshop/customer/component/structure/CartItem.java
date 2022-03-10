package de.leuphana.va.onlineshop.customer.component.structure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cartItemId;

	private int articleId;

	private int quantity;

	public CartItem() {
		quantity = 1;
	}

	public CartItem(int articleId) {
		this.articleId = articleId;
		quantity = 1;
	}

	public int getCartItemId() {
		return cartItemId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void incrementQuantity() {
		quantity++;
	}

	public void decrementQuantity() {
		quantity--;
	}

}