package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;

public class SupplierServiceImpl implements SupplierService {
    @Override
    public boolean insertArticle(Article article) {
        return false;
    }

    @Override
    public boolean updateArticle(Article article) {
        return false;
    }

    @Override
    public boolean removeArticle(int articleId) {
        return false;
    }

    @Override
    public Article getArticle(int articleId) {
        return null;
    }
}