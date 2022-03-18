package de.leuphana.va.onlineshop.article.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.va.onlineshop.article.component.structure.Article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

@Entity
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		property = "articleType",
		visible = true,
		defaultImpl = ArticleDto.class
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = CDDto.class, name = "CD"),
		@JsonSubTypes.Type(value = BookDto.class, name = "Book")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer articleId;
	private String manufacturer;
	private String name;
	private float price;

	public ArticleDto() {}

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

	public Article toArticle() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, this);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(writer.toString(), Article.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArticleDto fromArticle(Article article) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, article);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
			return mapper.readValue(writer.toString(), ArticleDto.class);

		} catch (IOException e) {
			System.out.println("VERKACKT");
			e.printStackTrace();
		}
		return null;
	}
}