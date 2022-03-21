package de.leuphana.va.onlineshop.shop.connector;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.requests.ArticleWriteRequest;
import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleWriteResponse;
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


    //================================================================================
    // CUSTOMER SERVICE
    //================================================================================

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
        Article article = customerService.getArticle(articleId);
        return new ArticleGetResponse(article);
    }

    @GetMapping("articles")
    public AllArticlesGetResponse getArticles() {
        Set<Article> articles = customerService.getArticles();
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


    //================================================================================
    // SUPPLIER SERVICE
    //================================================================================

    @GetMapping("supplier/article/{articleId}")
    public ArticleGetResponse getArticleForSupplier(@PathVariable("articleId") Integer articleId) {
        Article article = supplierService.getArticle(articleId);
        return new ArticleGetResponse(article);
    }

    @PostMapping("supplier/article/create")
    public ArticleWriteResponse insertArticle(@RequestBody ArticleWriteRequest requestBody) {
        Article article = requestBody.article();
        article.setArticleId(null);
        boolean success = supplierService.insertArticle(article);
        return new ArticleWriteResponse(success);
    }

    @PostMapping("supplier/article/update")
    public ArticleWriteResponse updateArticle(@RequestBody ArticleWriteRequest requestBody) {
        boolean success = supplierService.updateArticle(requestBody.article());
        return new ArticleWriteResponse(success);
    }

    @DeleteMapping("supplier/article/{articleId}")
    public ArticleWriteResponse removeArticle(@PathVariable("articleId") Integer articleId) {
        boolean success = supplierService.removeArticle(articleId);
        return new ArticleWriteResponse(success);
    }
}
