package de.leuphana.va.onlineshop.article.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.Catalog;
import de.leuphana.va.onlineshop.article.connector.ArticleSpringDataConnectorRequester;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ArticleComponentServiceImpl implements ArticleComponentService {

    // This service implementation manages one single catalog of articles.
    public static final String mainCatalogId = "main_catalog";

    private final ArticleSpringDataConnectorRequester dataConnector;

    public ArticleComponentServiceImpl(ArticleSpringDataConnectorRequester dataConnector) {
        this.dataConnector = dataConnector;

//        dataConnector.deleteCatalog(mainCatalogId);
        initializeMainCatalog();
    }

    /**
     * Ensures that the main catalog is created in data bank
     */
    private void initializeMainCatalog() {
        Catalog foundCatalog = dataConnector.findCatalogById(mainCatalogId);
        if (foundCatalog != null) return;

        Catalog newCatalog = new Catalog(mainCatalogId);
        newCatalog.setArticles(Catalog.getInitialArticles());
        dataConnector.saveCatalog(newCatalog);
    }

    @Override
    public Catalog getCatalog() {
        return dataConnector.findCatalogById(mainCatalogId);
    }

    @Override
    public Article getArticle(int articleId) {
        return dataConnector.findArticleById(articleId);
    }

    @Override
    public Set<Article> getArticles() {
        return dataConnector.findAllArticles();
    }

    @Override
    public boolean insertArticle(Article article) {
        dataConnector.insertArticleIntoCatalog(article, mainCatalogId);
        return true; // TODO: creation success?
    }

    @Override
    public boolean updateArticle(Article article) {
        return dataConnector.updateArticle(article);
    }

    @Override
    public boolean removeArticle(int articleId) {
        dataConnector.deleteArticle(articleId);
        return true; // TODO: removing success?
    }
}
