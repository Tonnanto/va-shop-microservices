package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;

public interface SupplierService {

    boolean insertArticle(Article article);

    boolean updateArticle(Article article);

    boolean removeArticle(int articleId);

    Article getArticle(int articleId);

}
