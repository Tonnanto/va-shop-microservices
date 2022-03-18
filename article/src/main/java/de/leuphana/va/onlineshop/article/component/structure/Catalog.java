package de.leuphana.va.onlineshop.article.component.structure;

import java.util.HashSet;
import java.util.Set;

public class Catalog {
    private String catalogId;
    private Set<Article> articles;

    public Catalog() {
        this.articles = new HashSet<>();
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
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
