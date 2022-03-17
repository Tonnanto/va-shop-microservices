package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.connector.requests.*;
import de.leuphana.va.onlineshop.customer.connector.responses.*;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import de.leuphana.va.onlineshop.shop.connector.ApiGatewayRestConnectorRequester;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    public CustomerServiceImpl(ApiGatewayRestConnectorRequester apiGatewayRestConnectorRequester) {
        this.apiGatewayRestConnectorRequester = apiGatewayRestConnectorRequester;
    }

    private final ApiGatewayRestConnectorRequester apiGatewayRestConnectorRequester;

    @Override
    public Integer createCustomerWithCart(String name, String address) {
        CustomerCreateRequest requestBody = new CustomerCreateRequest(name, address);
        CustomerCreateResponse responseBody = apiGatewayRestConnectorRequester.createCustomer(requestBody).getBody();
        if (responseBody == null) return null;
        return responseBody.customer().getCustomerId();
    }

    @Override
    public Article getArticle(int articleId) {
        ArticleGetResponse responseBody = apiGatewayRestConnectorRequester.getArticle(articleId).getBody();
        if (responseBody == null) return null;
        return responseBody.article();
    }

    @Override
    public Set<Article> getArticles() {
        AllArticlesGetResponse responseBody = apiGatewayRestConnectorRequester.getArticles().getBody();
        if (responseBody == null) return null;
        return responseBody.articles();
    }

    @Override
    public void removeArticleFromCart(Integer customerId, int articleId) {

    }

    @Override
    public void addArticleToCart(Integer customerId, Integer articleId) {

    }

    @Override
    public void decrementArticleQuantityInCart(Integer customerId, Integer articleId) {

    }

    @Override
    public Orderr checkOutCart(int customerId) {
        return null;
    }

    @Override
    public Cart getCartForCustomer(Integer customerId) {
        return null;
    }
}
