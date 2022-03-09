package de.leuphana.va.onlineshop.article.connector;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.configuration.ArticleConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = { ArticleConfiguration.class })
public class ArticleSpringDataConnectorRequesterTest {

    private static ArticleSpringDataConnectorRequester articleDataConnectorRequester;
    private static Article article;

    @BeforeAll
    static void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ArticleConfiguration.class);
        articleDataConnectorRequester = (ArticleSpringDataConnectorRequester) applicationContext.getBean("articleSpringDataConnectorRequester");

        article = new Article();
        article.setName("Toller Artikel");
        article.setPrice(420.69f);
    }

    @AfterAll
    static void tearDown() {
        articleDataConnectorRequester = null;
    }

    @Test
    @Order(1)
    void canArticleBeInserted() {
        articleDataConnectorRequester.insertArticle(article);
    }

    @Test
    @Order(2)
    void areAllArticlesAvailable() {
        Assertions.assertNotNull(articleDataConnectorRequester.findAllArticles());
    }

    @Test
    @Order(3)
    void canArticleBeFoundById() {
        Assertions.assertNotNull(articleDataConnectorRequester.findArticleById(1));
    }
}
