package de.leuphana.va.onlineshop.shop.connector;

import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.customer.connector.requests.*;
import de.leuphana.va.onlineshop.customer.connector.responses.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("API-GATEWAY")
public interface ApiGatewayRestConnectorRequester {

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
            method = RequestMethod.POST,
            value = "/api/v1/customer/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CustomerCreateResponse> createCustomer(@RequestBody CustomerCreateRequest requestBody);
}
