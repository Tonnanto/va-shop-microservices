package de.leuphana.va.onlineshop.article.connector;

import de.leuphana.va.onlineshop.article.component.behaviour.ArticleComponentService;
import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.Catalog;
import de.leuphana.va.onlineshop.article.component.structure.requests.ArticleWriteRequest;
import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleWriteResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.CatalogGetResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/article")
public class ArticleRestConnectorProvider {

    private final ArticleComponentService articleService;

    public ArticleRestConnectorProvider(ArticleComponentService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(path = "{articleId}")
    public ArticleGetResponse getArticle(@PathVariable("articleId") Integer articleId) {
        Article article = articleService.getArticle(articleId);
        return new ArticleGetResponse(article);
    }

    @GetMapping(path = "/all")
    public AllArticlesGetResponse getAllArticles() {
        Set<Article> articles = articleService.getArticles();
        return new AllArticlesGetResponse(articles);
    }

    @GetMapping(path = "/catalog")
    public CatalogGetResponse getCatalog() {
        Catalog catalog = articleService.getCatalog();
        return new CatalogGetResponse(catalog);
    }

    @PostMapping(path = "/create")
    public ArticleWriteResponse createArticle(@RequestBody ArticleWriteRequest createArticleRequest) {
        boolean success = articleService.insertArticle(createArticleRequest.article());
        return new ArticleWriteResponse(success);
    }

    @PostMapping(path = "/update")
    public ArticleWriteResponse updateArticle(@RequestBody ArticleWriteRequest updateArticleRequest) {
        boolean success = articleService.updateArticle(updateArticleRequest.article());
        return new ArticleWriteResponse(success);
    }

    @DeleteMapping(path = "/{articleId}")
    public ArticleWriteResponse updateArticle(@PathVariable("articleId") Integer articleId) {
        boolean success = articleService.removeArticle(articleId);
        return new ArticleWriteResponse(success);
    }

}
