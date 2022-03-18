package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Override
    public boolean insertArticle(ArticleDto article) {
        return false;
    }

    @Override
    public boolean updateArticle(ArticleDto article) {
        return false;
    }

    @Override
    public boolean removeArticle(int articleId) {
        return false;
    }

    @Override
    public ArticleDto getArticle(int articleId) {
        return null;
    }
}
