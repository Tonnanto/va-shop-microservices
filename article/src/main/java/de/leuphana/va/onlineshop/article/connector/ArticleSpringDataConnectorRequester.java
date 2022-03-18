package de.leuphana.va.onlineshop.article.connector;

import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;
import de.leuphana.va.onlineshop.article.connector.dto.CatalogDto;
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

    public void insertArticleIntoCatalog(ArticleDto article, String catalogId) {
        CatalogDto foundCatalog = findCatalogById(catalogId);
        if (foundCatalog == null) return;
        foundCatalog.addArticle(article);
        getCurrentSession().save(foundCatalog);
    }

    public Set<ArticleDto> findAllArticles() {
        Set<ArticleDto> articles = new HashSet<>();

        String queryString = "SELECT * FROM Article";

        NativeQuery<ArticleDto> query = getCurrentSession().createNativeQuery(queryString, ArticleDto.class);
        List<?> results = query.getResultList();

        for (Object result: results) {
            if (result instanceof ArticleDto article) {
                articles.add(article);
            }
        }

        return articles;
    }

    public ArticleDto findArticleById(int id) {
        return getCurrentSession().find(ArticleDto.class, id);
    }

    public boolean updateArticle(ArticleDto article) {
        try {
            getCurrentSession().update(article);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void deleteArticle(int id) {
        ArticleDto article = findArticleById(id);
        getCurrentSession().delete(article);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for Catalog
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void saveCatalog(CatalogDto catalog) {
        getCurrentSession().saveOrUpdate(catalog);
    }

    public Set<CatalogDto> findAllCatalogs() {
        Set<CatalogDto> catalogs = new HashSet<>();

        String queryString = "SELECT * FROM Catalog";

        NativeQuery<CatalogDto> query = getCurrentSession().createNativeQuery(queryString, CatalogDto.class);
        List<?> results = query.getResultList();

        for (Object result: results) {
            if (result instanceof CatalogDto catalog) {
                catalogs.add(catalog);
            }
        }

        return catalogs;
    }

    public CatalogDto findCatalogById(String id) {
        return getCurrentSession().find(CatalogDto.class, id);
    }

    public void deleteCatalog(String id) {
        CatalogDto catalog = findCatalogById(id);
        getCurrentSession().delete(catalog);
    }
}
