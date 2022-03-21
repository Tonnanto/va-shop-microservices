package de.leuphana.va.onlineshop.shop.connector;

import de.leuphana.va.onlineshop.article.component.structure.requests.ArticleWriteRequest;
import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleWriteResponse;
import de.leuphana.va.onlineshop.customer.connector.requests.AddRemoveArticleRequest;
import de.leuphana.va.onlineshop.customer.connector.requests.CustomerCreateRequest;
import de.leuphana.va.onlineshop.customer.connector.responses.CartGetResponse;
import de.leuphana.va.onlineshop.customer.connector.responses.CustomerReadResponse;
import de.leuphana.va.onlineshop.order.component.structure.requests.OrderCreateRequest;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrderGetResponse;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrderWriteResponse;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrdersGetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("API-GATEWAY")
public interface ApiGatewayRestConnectorRequester {


    // ++++++++++++++++++++++++++++++++++++++++ ARTICLE +++++++++++++++++++++++++++++++++++++++++++

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/v1/article/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AllArticlesGetResponse> getArticles();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/v1/article/{articleId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ArticleGetResponse> getArticle(@PathVariable("articleId") Integer articleId);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/v1/article/catalog",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AllArticlesGetResponse> getCatalog();

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/article/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ArticleWriteResponse> createArticle(@RequestBody ArticleWriteRequest requestBody);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/article/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ArticleWriteResponse> updateArticle(@RequestBody ArticleWriteRequest requestBody);

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/v1/article/{articleId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ArticleWriteResponse> deleteArticle(@PathVariable("articleId") Integer articleId);


    // ++++++++++++++++++++++++++++++++++++++++ CUSTOMER +++++++++++++++++++++++++++++++++++++++++++

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/customer/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CustomerReadResponse> createCustomer(@RequestBody CustomerCreateRequest requestBody);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/v1/customer/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CustomerReadResponse> getCustomer(@PathVariable("customerId") Integer customerId);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/v1/customer/cart/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CartGetResponse> findCartForCustomer(@PathVariable("customerId") Integer customerId);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/customer/cart/addArticle",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Object> addArticleToCart(@RequestBody AddRemoveArticleRequest requestBody);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/customer/cart/removeArticle",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Object> removeArticleFromCart(@RequestBody AddRemoveArticleRequest requestBody);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/customer/cart/decreaseArticle",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Object> cartItemDecreaseQuantity(@RequestBody AddRemoveArticleRequest requestBody);


    // ++++++++++++++++++++++++++++++++++++++++ ORDER +++++++++++++++++++++++++++++++++++++++++++

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/v1/order/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<OrderWriteResponse> createOrder(@RequestBody OrderCreateRequest requestBody);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/v1/order/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<OrdersGetResponse> getOrders();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/v1/order/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<OrderGetResponse> getOrder(@PathVariable("orderId") Integer orderId);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/v1/order/customer/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<OrdersGetResponse> getOrdersForCustomer(@PathVariable("customerId") Integer customerId);

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/v1/order/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AllArticlesGetResponse> deleteOrder(@PathVariable("orderId") Integer orderId);
}
