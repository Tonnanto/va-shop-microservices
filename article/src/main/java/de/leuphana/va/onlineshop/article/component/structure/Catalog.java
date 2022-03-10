package de.leuphana.va.onlineshop.article.component.structure;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Catalog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int catalogId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Article> articles;

	public Catalog() {}

	public int getCatalogId() {
		return catalogId;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public void addArticle(Article article) {
		this.articles.add(article);
	}

	public Article getArticle(int articleId) {
		Article foundArticle = null;

		for (Article article : articles) {
			if (article.getArticleId() == articleId) {
				foundArticle = article;
				break;
			}
		}

		return foundArticle;
	}


}
