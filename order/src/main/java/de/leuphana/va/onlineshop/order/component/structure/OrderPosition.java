package de.leuphana.va.onlineshop.order.component.structure;

import de.leuphana.va.onlineshop.article.component.structure.Article;

public class OrderPosition {

	private int positionId;
	private Article article;
	private int articleQuantity;

	public OrderPosition() {}

	public OrderPosition(int positionId) {
		this.positionId = positionId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getArticleQuantity() {
		return articleQuantity;
	}

	public void setArticleQuantity(int articleQuantity) {
		this.articleQuantity = articleQuantity;
	}

}