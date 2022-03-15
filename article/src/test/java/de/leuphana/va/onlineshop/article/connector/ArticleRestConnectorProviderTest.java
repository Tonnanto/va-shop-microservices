package de.leuphana.va.onlineshop.article.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.va.onlineshop.article.component.ArticleApplication;
import de.leuphana.va.onlineshop.article.component.behaviour.ArticleComponentServiceImpl;
import de.leuphana.va.onlineshop.article.component.structure.Catalog;
import de.leuphana.va.onlineshop.article.component.structure.responses.CatalogGetResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@SpringBootTest(classes = ArticleApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class ArticleRestConnectorProviderTest {

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
        Assertions.assertTrue(catalog.getArticles().size() > 0);
    }

    // TODO: canArticleBeInserted
    // TODO: canArticleBeFound
    // TODO: canAllArticlesBeFound
    // TODO: canArticleBeUpdated
    // TODO: canArticleBeDeleted

}
