package de.leuphana.va.onlineshop.article.connector;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.Catalog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class ArticleSpringDataConnectorRequester {

    public ArticleSpringDataConnectorRequester() {
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for Article
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void insertArticleIntoCatalog(Article article, int catalogId) {
        Catalog foundCatalog = findCatalogById(catalogId);
        if (foundCatalog == null) return;
        foundCatalog.addArticle(article);
        getCurrentSession().save(foundCatalog);
    }

    public Set<Article> findAllArticles() {
        Set<Article> articles = new HashSet<>();

        String queryString = "SELECT * FROM Article";

        NativeQuery<Article> query = getCurrentSession().createNativeQuery(queryString, Article.class);
        List<?> results = query.getResultList();

        for (Object result: results) {
            if (result instanceof Article article) {
                articles.add(article);
            }
        }

        return articles;
    }

    public Article findArticleById(int id) {
        return getCurrentSession().find(Article.class, id);
    }

    public void deleteArticle(int id) {
        Article article = findArticleById(id);
        getCurrentSession().delete(article);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for Catalog
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void saveCatalog(Catalog catalog) {
        getCurrentSession().save(catalog);
    }

    public Set<Catalog> findAllCatalogs() {
        Set<Catalog> catalogs = new HashSet<>();

        String queryString = "SELECT * FROM Catalog";

        NativeQuery<Catalog> query = getCurrentSession().createNativeQuery(queryString, Catalog.class);
        List<?> results = query.getResultList();

        for (Object result: results) {
            if (result instanceof Catalog catalog) {
                catalogs.add(catalog);
            }
        }

        return catalogs;
    }

    public Catalog findCatalogById(int id) {
        return getCurrentSession().find(Catalog.class, id);
    }

    public void deleteCatalog(int id) {
        Catalog catalog = findCatalogById(id);
        getCurrentSession().delete(catalog);
    }
}
