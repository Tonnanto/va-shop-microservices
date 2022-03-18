package de.leuphana.va.onlineshop.article.component.behaviour;

import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;
import de.leuphana.va.onlineshop.article.connector.dto.CatalogDto;

import java.util.Set;

public interface ArticleComponentService {

    CatalogDto getCatalog();

    ArticleDto getArticle(int articleId);

    Set<ArticleDto> getArticles();

    boolean insertArticle(ArticleDto article);

    boolean updateArticle(ArticleDto article);

    boolean removeArticle(int articleId);

}
