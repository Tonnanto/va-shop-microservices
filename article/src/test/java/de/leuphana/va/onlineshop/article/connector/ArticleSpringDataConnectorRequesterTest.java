package de.leuphana.va.onlineshop.article.connector;

import de.leuphana.va.onlineshop.article.component.structure.*;
import de.leuphana.va.onlineshop.article.configuration.ArticleConfiguration;
import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;
import de.leuphana.va.onlineshop.article.connector.dto.BookDto;
import de.leuphana.va.onlineshop.article.connector.dto.CDDto;
import de.leuphana.va.onlineshop.article.connector.dto.CatalogDto;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = { ArticleConfiguration.class })
public class ArticleSpringDataConnectorRequesterTest {

    private static ArticleSpringDataConnectorRequester articleDataConnectorRequester;
    private static Set<ArticleDto> articleSet;
    private static CatalogDto catalog;

    @BeforeAll
    static void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ArticleConfiguration.class);
        articleDataConnectorRequester = (ArticleSpringDataConnectorRequester) applicationContext.getBean("articleSpringDataConnectorRequester");

        ArticleDto article1 = new ArticleDto();
        article1.setName("Toller Artikel");
        article1.setPrice(420.69f);

        BookDto article2 = new BookDto();
        article2.setName("Tolles Buch");
        article2.setPrice(9.99f);
        article2.setManufacturer("Springer Verlag");
        article2.setAuthor("Anton Stamme");
        article2.setBookCategory(BookCategory.CRIME);

        CDDto article3 = new CDDto();
        article3.setName("Tolle CD");
        article3.setPrice(420.69f);
        article3.setArtist("Ranji & Berg");
        article3.setManufacturer("VooV");

        articleSet = new HashSet<>(Arrays.asList(article1, article2, article3));

        catalog = new CatalogDto();
        catalog.setArticles(articleSet);
    }

    @AfterAll
    static void tearDown() {
        articleDataConnectorRequester = null;
        articleSet = null;
    }

    @Test
    @Order(1)
    void canCatalogBeCreated() {
        articleDataConnectorRequester.saveCatalog(catalog);
    }

    @Test
    @Order(2)
    void canCatalogBeFound() {
        Assertions.assertNotNull(articleDataConnectorRequester.findCatalogById(catalog.getCatalogId()));
    }

    @Test
    @Order(3)
    void canArticleBeInsertedIntoCatalog() {
        BookDto book = new BookDto();
        book.setName("Java in a nutshell");
        book.setPrice(10.5f);
        book.setBookCategory(BookCategory.POPULAR_SCIENCE);

        articleDataConnectorRequester.insertArticleIntoCatalog(book, catalog.getCatalogId());
        Assertions.assertNotNull(articleDataConnectorRequester.findArticleById(book.getArticleId()));
    }


    @Test
    @Order(4)
    void areAllArticlesAvailable() {
        Set<ArticleDto> foundArticles = articleDataConnectorRequester.findAllArticles();
        for (ArticleDto article: articleSet) {
            Assertions.assertTrue(foundArticles.stream().anyMatch(article1 -> article1.getArticleId() == article.getArticleId()));
        }
    }

    @Test
    @Order(5)
    void canArticleBeFoundById() {
        for (ArticleDto article: articleSet) {
            Assertions.assertNotNull(articleDataConnectorRequester.findArticleById(article.getArticleId()));
        }
    }

    @Test
    @Order(6)
    void canArticleBeDeleted() {
        int articleToDelete = articleSet.stream().findFirst().get().getArticleId();
        Assertions.assertNotNull(articleDataConnectorRequester.findArticleById(articleToDelete));
        articleDataConnectorRequester.deleteArticle(articleToDelete);
        Assertions.assertNull(articleDataConnectorRequester.findArticleById(articleToDelete));
    }

    @Test
    @Order(7)
    void canCatalogBeDeleted() {
        articleDataConnectorRequester.deleteCatalog(catalog.getCatalogId());
        Assertions.assertNull(articleDataConnectorRequester.findCatalogById(catalog.getCatalogId()));
    }
}
