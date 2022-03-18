package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;

public interface SupplierService {

    boolean insertArticle(ArticleDto article);

    boolean updateArticle(ArticleDto article);

    boolean removeArticle(int articleId);

    ArticleDto getArticle(int articleId);

}
