package de.leuphana.va.onlineshop.article.component.structure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Article implements Serializable {

	@Id
	@GeneratedValue
	private int articleId;
	private String manufacturer;
	private String name;
	private float price;

	public Article() {}
	public Article(int articleId) {
		this.articleId = articleId;
	}

	public int getArticleId() {
		return articleId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}