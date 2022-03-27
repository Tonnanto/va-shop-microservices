package de.leuphana.va.onlineshop.article.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.va.onlineshop.article.component.ArticleApplication;
import de.leuphana.va.onlineshop.article.component.behaviour.ArticleComponentServiceImpl;
import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.Book;
import de.leuphana.va.onlineshop.article.component.structure.BookCategory;
import de.leuphana.va.onlineshop.article.component.structure.Catalog;
import de.leuphana.va.onlineshop.article.component.structure.requests.ArticleWriteRequest;
import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleWriteResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.CatalogGetResponse;
import de.leuphana.va.onlineshop.article.configuration.ArticleTestConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Set;

@SpringBootTest(classes = ArticleApplication.class)
@Import(ArticleTestConfiguration.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ArticleRestConnectorProviderTest {

    public static Integer articleId;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    @Order(0)
    public void canCatalogBeFound() throws Exception {
        String uri = "/api/v1/article/catalog";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        CatalogGetResponse catalogGetResponse = mapFromJson(content, CatalogGetResponse.class);
        Catalog catalog = catalogGetResponse.catalog();

        Assertions.assertNotNull(catalog);
        Assertions.assertEquals(ArticleComponentServiceImpl.mainCatalogId, catalog.getCatalogId());
    }

    @Test
    @Order(1)
    public void canArticleBeInserted() throws Exception {

        Book newArticle = new Book();
        newArticle.setName("testBook");
        newArticle.setPrice(123.45f);
        newArticle.setBookCategory(BookCategory.CRIME);
        newArticle.setAuthor("Anton");
        newArticle.setManufacturer("Springer oder so");

        ArticleWriteRequest requestBody = new ArticleWriteRequest(newArticle);

        String requestJson = mapToJson(requestBody);

        String uri = "/api/v1/article/create";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
                .characterEncoding("utf-8"))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ArticleWriteResponse articleWriteResponse = mapFromJson(content, ArticleWriteResponse.class);
        boolean success = articleWriteResponse.success();

        Assertions.assertTrue(success);
    }

    @Test
    @Order(2)
    public void canAllArticlesBeFound() throws Exception {
        String uri = "/api/v1/article/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        AllArticlesGetResponse allArticlesGetResponse = mapFromJson(content, AllArticlesGetResponse.class);
        Set<Article> articles = allArticlesGetResponse.articles();

        Assertions.assertNotNull(articles);
        Assertions.assertTrue(articles.size() > 0);

        articleId = articles.stream().findFirst().get().getArticleId();
    }

    @Test
    @Order(3)
    public void canArticleBeFound() throws Exception {
        String uri = "/api/v1/article/" + articleId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        ArticleGetResponse articleGetResponse = mapFromJson(content, ArticleGetResponse.class);
        Article article = articleGetResponse.article();

        Assertions.assertNotNull(article);
        Assertions.assertEquals(articleId, article.getArticleId());
    }

    @Test
    @Order(4)
    public void canArticleBeUpdated() throws Exception {

        Book updatedArticle = new Book();
        updatedArticle.setArticleId(articleId);
        updatedArticle.setName("testBook 123");
        updatedArticle.setPrice(420.69f);
        updatedArticle.setBookCategory(BookCategory.POPULAR_SCIENCE);
        updatedArticle.setAuthor("Antn");
        updatedArticle.setManufacturer("Springer oder so");

        ArticleWriteRequest requestBody = new ArticleWriteRequest(updatedArticle);

        String requestJson = mapToJson(requestBody);

        String uri = "/api/v1/article/update";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                        .characterEncoding("utf-8"))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ArticleWriteResponse articleWriteResponse = mapFromJson(content, ArticleWriteResponse.class);
        boolean success = articleWriteResponse.success();

        Assertions.assertTrue(success);
    }

    @Test
    @Order(5)
    public void canArticleBeDeleted() throws Exception {
        String uri = "/api/v1/article/" + articleId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ArticleWriteResponse articleWriteResponse = mapFromJson(content, ArticleWriteResponse.class);
        boolean success = articleWriteResponse.success();

        Assertions.assertTrue(success);
    }
}
