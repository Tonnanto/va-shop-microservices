package de.leuphana.va.onlineshop.article.component.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "articleType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CD.class, name = "CD"),
        @JsonSubTypes.Type(value = Book.class, name = "Book")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer articleId;
    private String manufacturer;
    private String name;
    private float price;

    public Article() {
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
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

    public void setPrice(float price) {
        this.price = price;
    }
}