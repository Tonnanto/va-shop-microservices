package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.component.structure.CartItem;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.customer.connector.requests.AddRemoveArticleRequest;
import de.leuphana.va.onlineshop.customer.connector.requests.CustomerCreateRequest;
import de.leuphana.va.onlineshop.customer.connector.responses.CartGetResponse;
import de.leuphana.va.onlineshop.customer.connector.responses.CustomerReadResponse;
import de.leuphana.va.onlineshop.order.component.structure.OrderPosition;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import de.leuphana.va.onlineshop.order.component.structure.requests.OrderCreateRequest;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrderWriteResponse;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrdersGetResponse;
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
        CustomerReadResponse responseBody = apiGatewayRestConnectorRequester.createCustomer(requestBody).getBody();
        if (responseBody == null) return null;
        return responseBody.customer().getCustomerId();
    }

    @Override
    public Customer getCustomer(Integer customerId) {
        CustomerReadResponse responseBody = apiGatewayRestConnectorRequester.getCustomer(customerId).getBody();
        if (responseBody == null) return null;
        return responseBody.customer();
    }

    @Override
    public Article getArticle(Integer articleId) {
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
    public void removeArticleFromCart(Integer customerId, Integer articleId) {
        AddRemoveArticleRequest requestBody = new AddRemoveArticleRequest(customerId, articleId);
        apiGatewayRestConnectorRequester.removeArticleFromCart(requestBody);
    }

    @Override
    public void addArticleToCart(Integer customerId, Integer articleId) {
        // TODO: Does article with articleId exist?
        AddRemoveArticleRequest requestBody = new AddRemoveArticleRequest(customerId, articleId);
        apiGatewayRestConnectorRequester.addArticleToCart(requestBody);
    }

    @Override
    public void decrementArticleQuantityInCart(Integer customerId, Integer articleId) {
        AddRemoveArticleRequest requestBody = new AddRemoveArticleRequest(customerId, articleId);
        apiGatewayRestConnectorRequester.cartItemDecreaseQuantity(requestBody);
    }

    @Override
    public boolean checkOutCart(Integer customerId) {
        // find cart
        Cart cart = getCartForCustomer(customerId);

        // check cart is not empty
        if (cart.getCartItems().isEmpty()) return false;

        // create order
        Orderr order = new Orderr();
        order.setCustomerId(customerId);

        // for every cart item
        for (CartItem cartItem: cart.getCartItems()) {
            // remove cart item from cart
            AddRemoveArticleRequest removeArticleRequest = new AddRemoveArticleRequest(customerId, cartItem.getArticleId());
            apiGatewayRestConnectorRequester.removeArticleFromCart(removeArticleRequest);

            // add cart item to order
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setArticleId(cartItem.getArticleId());
            orderPosition.setArticleQuantity(cartItem.getQuantity());
            order.addOrderPosition(orderPosition);
        }

        // save order
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(order);
        OrderWriteResponse orderCreateResponse = apiGatewayRestConnectorRequester.createOrder(orderCreateRequest).getBody();
//        OrderGetResponse orderGetResponse = apiGatewayRestConnectorRequester.getOrder(order.getOrderId()).getBody();
        if (orderCreateResponse == null) return false;
        return orderCreateResponse.success();
    }

    @Override
    public Set<Orderr> getOrdersForCustomer(Integer customerId) {
        OrdersGetResponse responseBody = apiGatewayRestConnectorRequester.getOrdersForCustomer(customerId).getBody();
        if (responseBody == null) return null;
        return responseBody.orders();
    }

    @Override
    public Cart getCartForCustomer(Integer customerId) {
        CartGetResponse responseBody = apiGatewayRestConnectorRequester.findCartForCustomer(customerId).getBody();
        if (responseBody == null) return null;
        return responseBody.cart();
    }
}
