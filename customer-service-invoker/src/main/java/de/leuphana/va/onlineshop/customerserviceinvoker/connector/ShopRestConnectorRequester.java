package de.leuphana.va.onlineshop.customerserviceinvoker.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.customer.connector.requests.AddRemoveArticleRequest;
import de.leuphana.va.onlineshop.customer.connector.requests.CustomerCreateRequest;
import de.leuphana.va.onlineshop.customer.connector.responses.CustomerReadResponse;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrderWriteResponse;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrdersGetResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Set;

public class ShopRestConnectorRequester {

    CloseableHttpClient httpclient;

    public ShopRestConnectorRequester() {
        this.httpclient = HttpClients.createDefault();
    }

    public Set<Article> getAllArticles() throws Exception {
        HttpGet httpGet = new HttpGet("http://localhost:42069/articles");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);

        String responseString = EntityUtils.toString(response1.getEntity());
        AllArticlesGetResponse response = mapFromJson(responseString, AllArticlesGetResponse.class);
        return response.articles();
    }

    public Article getArticle(int articleId) throws Exception {
        HttpGet httpGet = new HttpGet("http://localhost:42069/article/" + articleId);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);

        String responseString = EntityUtils.toString(response1.getEntity());
        ArticleGetResponse response = mapFromJson(responseString, ArticleGetResponse.class);
        return response.article();
    }

    public boolean addArticleToCart(int customerId, int articleId) throws Exception {
        HttpPost httpPost = new HttpPost("http://localhost:42069/cart/addArticle");

        String json = mapToJson(new AddRemoveArticleRequest(customerId, articleId));
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response1 = httpclient.execute(httpPost);
        return response1.getStatusLine().getStatusCode() == 200;
    }

    public boolean removeArticleFromCart(int customerId, int articleId) throws Exception {
        HttpPost httpPost = new HttpPost("http://localhost:42069/cart/removeArticle");

        String json = mapToJson(new AddRemoveArticleRequest(customerId, articleId));
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response1 = httpclient.execute(httpPost);
        return response1.getStatusLine().getStatusCode() == 200;
    }

    public Customer createCustomer(String name, String address) throws Exception {

        HttpPost httpPost = new HttpPost("http://localhost:42069/customer/create");

        String json = mapToJson(new CustomerCreateRequest(name, address));

        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response1 = httpclient.execute(httpPost);
        String responseString = EntityUtils.toString(response1.getEntity());
        int newCustomerId = Integer.parseInt(responseString);

        return getCustomerWithId(newCustomerId);
    }

    public Customer getCustomerWithId(int customerId) throws Exception {

        HttpGet httpGet = new HttpGet("http://localhost:42069/customer/" + customerId);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);

        String responseString = EntityUtils.toString(response1.getEntity());
        CustomerReadResponse response = mapFromJson(responseString, CustomerReadResponse.class);
        return response.customer();
    }

    public Set<Orderr> getOrdersForCustomer(int customerId) throws Exception {
        HttpGet httpGet = new HttpGet("http://localhost:42069/orders/" + customerId);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);

        String responseString = EntityUtils.toString(response1.getEntity());
        OrdersGetResponse response = mapFromJson(responseString, OrdersGetResponse.class);
        return response.orders();
    }

    public boolean checkOutCart(int customerId) throws Exception {
        HttpPost httpPost = new HttpPost("http://localhost:42069/cart/checkout/" + customerId);
        CloseableHttpResponse response1 = httpclient.execute(httpPost);

        String responseString = EntityUtils.toString(response1.getEntity());
        OrderWriteResponse response = mapFromJson(responseString, OrderWriteResponse.class);
        return response.success();
    }

    //================================================================================
    // Helper Methods
    //================================================================================

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
}
