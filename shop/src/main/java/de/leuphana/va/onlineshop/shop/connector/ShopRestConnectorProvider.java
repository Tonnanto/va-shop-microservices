package de.leuphana.va.onlineshop.shop.connector;

import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;
import de.leuphana.va.onlineshop.article.connector.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.connector.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.connector.requests.AddRemoveArticleRequest;
import de.leuphana.va.onlineshop.customer.connector.requests.CustomerCreateRequest;
import de.leuphana.va.onlineshop.customer.connector.responses.CartGetResponse;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrderGetResponse;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrdersGetResponse;
import de.leuphana.va.onlineshop.shop.component.behaviour.CustomerService;
import de.leuphana.va.onlineshop.shop.component.behaviour.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ShopRestConnectorProvider {

    private final CustomerService customerService;
    private final SupplierService supplierService;

    public ShopRestConnectorProvider(CustomerService customerService, SupplierService supplierService) {
        this.customerService = customerService;
        this.supplierService = supplierService;
    }

    @PostMapping("customer/create")
    public Integer createCustomerWithCart(@RequestBody CustomerCreateRequest requestBody) {
        return customerService.createCustomerWithCart(requestBody.name(), requestBody.address());
    }

    @GetMapping("article/{articleId}")
    public ArticleGetResponse getArticle(@PathVariable("articleId") Integer articleId) {
        ArticleDto article = customerService.getArticle(articleId);
        return new ArticleGetResponse(article);
    }

    @GetMapping("articles")
    public AllArticlesGetResponse getArticles() {
        Set<ArticleDto> articles = customerService.getArticles();
        return new AllArticlesGetResponse(articles);
    }

    @PostMapping("cart/removeArticle")
    public void removeArticleFromCart(@RequestBody AddRemoveArticleRequest requestBody) {
        customerService.removeArticleFromCart(requestBody.customerId(), requestBody.articleId());
    }

    @PostMapping("cart/addArticle")
    public void addArticleToCart(@RequestBody AddRemoveArticleRequest requestBody) {
        customerService.addArticleToCart(requestBody.customerId(), requestBody.articleId());
    }

    @PostMapping("cart/decreaseArticle")
    public void decrementArticleQuantityInCart(@RequestBody AddRemoveArticleRequest requestBody) {
        customerService.decrementArticleQuantityInCart(requestBody.customerId(), requestBody.articleId());
    }

    @PostMapping("cart/checkout/{customerId}")
    public OrderGetResponse checkOutCart(@PathVariable("customerId") Integer customerId) {
        Orderr order = customerService.checkOutCart(customerId);
        return new OrderGetResponse(order);
    }

    @GetMapping("cart/{customerId}")
    public CartGetResponse getCartForCustomer(@PathVariable("customerId") Integer customerId) {
        Cart cart = customerService.getCartForCustomer(customerId);
        return new CartGetResponse(cart);
    }

    @GetMapping("orders/{customerId}")
    public OrdersGetResponse getOrdersForCustomer(@PathVariable("customerId") Integer customerId) {
        Set<Orderr> orders = customerService.getOrdersForCustomer(customerId);
        return new OrdersGetResponse(orders);
    }

}
