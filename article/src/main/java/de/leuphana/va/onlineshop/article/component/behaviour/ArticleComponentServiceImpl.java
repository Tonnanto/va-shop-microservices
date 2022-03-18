package de.leuphana.va.onlineshop.article.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.BookCategory;
import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;
import de.leuphana.va.onlineshop.article.connector.dto.BookDto;
import de.leuphana.va.onlineshop.article.connector.dto.CDDto;
import de.leuphana.va.onlineshop.article.connector.dto.CatalogDto;
import de.leuphana.va.onlineshop.article.connector.ArticleSpringDataConnectorRequester;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
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
        CatalogDto foundCatalog = dataConnector.findCatalogById(mainCatalogId);
        if (foundCatalog != null) return;

        CatalogDto newCatalog = new CatalogDto(mainCatalogId);
        newCatalog.setArticles(getInitialArticles());
        dataConnector.saveCatalog(newCatalog);
    }

    @Override
    public CatalogDto getCatalog() {
        return dataConnector.findCatalogById(mainCatalogId);
    }

    @Override
    public ArticleDto getArticle(int articleId) {
        return dataConnector.findArticleById(articleId);
    }

    @Override
    public Set<ArticleDto> getArticles() {
        return dataConnector.findAllArticles();
    }

    @Override
    public boolean insertArticle(ArticleDto article) {
        dataConnector.insertArticleIntoCatalog(article, mainCatalogId);
        return true; // TODO: creation success?
    }

    @Override
    public boolean updateArticle(ArticleDto article) {
        return dataConnector.updateArticle(article);
    }

    @Override
    public boolean removeArticle(int articleId) {
        dataConnector.deleteArticle(articleId);
        return true; // TODO: removing success?
    }

    /**
     * @return a demo Catalog to populate the database with.
     */
    public Set<ArticleDto> getInitialArticles() {
        Set<ArticleDto> articles = new HashSet<>();

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

        BookDto article4 = new BookDto();
        article4.setName("Entwickeln von Web-Anwendungen");
        article4.setPrice(23.0f);
        article4.setBookCategory(BookCategory.POPULAR_SCIENCE);

        BookDto article5 = new BookDto();
        article5.setName("Java in a nutshell");
        article5.setPrice(10.5f);
        article5.setBookCategory(BookCategory.POPULAR_SCIENCE);

        BookDto article6 = new BookDto();
        article6.setName("Servlets");
        article6.setPrice(16.5f);
        article6.setBookCategory(BookCategory.POPULAR_SCIENCE);

        return new HashSet<>(Arrays.asList(article1, article2, article3, article4, article5, article6));
    }
}
