package de.leuphana.va.onlineshop.customer.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.va.onlineshop.customer.component.CustomerApplication;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.customer.configuration.CustomerTestConfiguration;
import de.leuphana.va.onlineshop.customer.connector.requests.AddRemoveArticleRequest;
import de.leuphana.va.onlineshop.customer.connector.requests.CustomerCreateRequest;
import de.leuphana.va.onlineshop.customer.connector.responses.CartGetResponse;
import de.leuphana.va.onlineshop.customer.connector.responses.CustomerReadResponse;
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

@SpringBootTest(classes = CustomerApplication.class)
@Import(CustomerTestConfiguration.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class CustomerRestConnectorProviderTest {

    public static Integer customerId;

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
    public void canCustomerWithCartBeCreated() throws Exception {

        String name = "Jonah Schwan";
        String address = "Schaeferdresch 1b";
        CustomerCreateRequest requestBody = new CustomerCreateRequest(name, address);

        String requestJson = mapToJson(requestBody);

        String uri = "/api/v1/customer/create";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                        .characterEncoding("utf-8"))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        CustomerReadResponse customerReadResponse = mapFromJson(content, CustomerReadResponse.class);

        Customer createdCustomer = customerReadResponse.customer();

        Assertions.assertEquals(name, createdCustomer.getName());
        Assertions.assertEquals(address, createdCustomer.getAddress());

        customerId = createdCustomer.getCustomerId();
    }

    @Test
    @Order(1)
    public void canCustomerBeFound() throws Exception {
        String uri = "/api/v1/customer/" + customerId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        CustomerReadResponse customerReadResponse = mapFromJson(content, CustomerReadResponse.class);
        Customer customer = customerReadResponse.customer();

        Assertions.assertNotNull(customer);
        Assertions.assertEquals(customerId, customer.getCustomerId());
    }

    @Test
    @Order(2)
    public void canArticleBeAddedToCart() throws Exception {

        int articleId = 420;
        AddRemoveArticleRequest requestBody = new AddRemoveArticleRequest(customerId, articleId);

        String requestJson = mapToJson(requestBody);

        String uri = "/api/v1/customer/cart/addArticle";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                        .characterEncoding("utf-8"))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        MvcResult mvcCartResult = mvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/cart/" + customerId))
                .andReturn();

        Assertions.assertEquals(200, mvcCartResult.getResponse().getStatus());
        String content = mvcCartResult.getResponse().getContentAsString();
        CartGetResponse cartGetResponse = mapFromJson(content, CartGetResponse.class);

        Assertions.assertNotNull(cartGetResponse.cart());
        Assertions.assertTrue(cartGetResponse.cart().getNumberOfArticles() > 0);
        Assertions.assertTrue(cartGetResponse.cart().getCartItems().stream().anyMatch(cartItem -> cartItem.getArticleId() == articleId));
    }

//    @Test
//    @Order(1)
//    public void canAllCustomersBeFound() throws Exception {
//        String uri = "/api/v1/customer/all";
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        Assertions.assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        AllArticlesGetResponse allArticlesGetResponse = mapFromJson(content, AllArticlesGetResponse.class);
//        Set<Article> articles = allArticlesGetResponse.articles();
//
//        Assertions.assertNotNull(articles);
//        Assertions.assertTrue(articles.size() > 0);
//
//        articleId = articles.stream().findFirst().get().getArticleId();
//    }

//    @Test
//    @Order(3)
//    public void canCustomerBeUpdated() throws Exception {
//
//        String name = "Heinrich Hase";
//        String address = "Sülfeld...";
//        CustomerCreateRequest requestBody = new CustomerCreateRequest(name, address);
//
//        String requestJson = mapToJson(requestBody);
//
//        String uri = "/api/v1/customer/update";
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestJson)
//                        .characterEncoding("utf-8"))
//                .andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        Assertions.assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        ArticleWriteResponse articleWriteResponse = mapFromJson(content, ArticleWriteResponse.class);
//        boolean success = articleWriteResponse.success();
//
//        Assertions.assertTrue(success);
//    }

//    @Test
//    @Order(4)
//    public void canCustomerBeDeleted() throws Exception {
//        String uri = "/api/v1/article/" + articleId;
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri))
//                .andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        Assertions.assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        ArticleWriteResponse articleWriteResponse = mapFromJson(content, ArticleWriteResponse.class);
//        boolean success = articleWriteResponse.success();
//
//        Assertions.assertTrue(success);
//    }

}
