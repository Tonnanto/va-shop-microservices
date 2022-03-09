package de.leuphana.va.onlineshop.article.connector;

import de.leuphana.va.onlineshop.article.component.structure.Article;
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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for Article
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void insertArticle(Article article) {
        getCurrentSession().save(article);
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

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
