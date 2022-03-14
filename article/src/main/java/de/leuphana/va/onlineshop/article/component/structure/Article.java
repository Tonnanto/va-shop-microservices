package de.leuphana.va.onlineshop.article.component.structure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "articleType", visible = true)
@JsonSubTypes({
		@JsonSubTypes.Type(value = CD.class, name = "CD"),
		@JsonSubTypes.Type(value = Book.class, name = "Book")
})
public class Article implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer articleId;
	private String manufacturer;
	private String name;
	private float price;

//	@ManyToOne(fetch = FetchType.LAZY)
//	private Catalog catalog;

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

//	public void setCatalog(Catalog catalog) {
//		this.catalog = catalog;
//	}
}