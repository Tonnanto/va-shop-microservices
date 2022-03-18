package de.leuphana.va.onlineshop.article.connector.dto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class CatalogDto {

	@Id
	private String catalogId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Set<ArticleDto> articles;

	public CatalogDto() {
		this.articles = new HashSet<>();
		this.catalogId = UUID.randomUUID().toString();
	}

	public CatalogDto(String id) {
		this.catalogId = id;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public Set<ArticleDto> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleDto> articles) {
		this.articles = articles;
	}

	public void addArticle(ArticleDto article) {
		this.articles.add(article);
	}

	public void addArticles(Set<ArticleDto> articles) {
		this.articles.addAll(articles);
	}

	public ArticleDto getArticle(int articleId) {
		ArticleDto foundArticle = null;

		for (ArticleDto article : articles) {
			if (article.getArticleId() == articleId) {
				foundArticle = article;
				break;
			}
		}

		return foundArticle;
	}
}
