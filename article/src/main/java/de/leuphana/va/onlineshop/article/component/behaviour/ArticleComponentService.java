package de.leuphana.va.onlineshop.article.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.Catalog;

import java.util.Set;

public interface ArticleComponentService {

    Catalog getCatalog();

    Article getArticle(int articleId);

    Set<Article> getArticles();

    boolean insertArticle(Article article);

    boolean updateArticle(Article article);

    boolean removeArticle(int articleId);

}
