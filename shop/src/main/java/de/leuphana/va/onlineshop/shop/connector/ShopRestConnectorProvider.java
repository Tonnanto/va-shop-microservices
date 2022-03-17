package de.leuphana.va.onlineshop.shop.connector;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.responses.AllArticlesGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.customer.connector.requests.*;
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
        Article article = customerService.getArticle(articleId);
        return new ArticleGetResponse(article);
    }

    @GetMapping("articles")
    public AllArticlesGetResponse getArticles() {
        Set<Article> articles = customerService.getArticles();
        return new AllArticlesGetResponse(articles);
    }

//    @DeleteMapping
//    public void removeArticleFromCart(Integer customerId, int articleId) {
//
//    }
//
//    @PostMapping
//    public void addArticleToCart(Integer customerId, Integer articleId) {
//
//    }
//
//    @PostMapping
//    public void decrementArticleQuantityInCart(Integer customerId, Integer articleId) {
//
//    }
//
//    @PostMapping
//    public Orderr checkOutCart(int customerId) {
//        return null;
//    }
//
//    @GetMapping
//    public Cart getCartForCustomer(Integer customerId) {
//        return null;
//    }

}
