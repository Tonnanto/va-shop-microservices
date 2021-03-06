package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;

import java.util.Set;

public interface CustomerService {

    Integer createCustomerWithCart(String name, String address);

    Customer getCustomer(Integer customerId);

    Article getArticle(Integer articleId);

    Set<Article> getArticles();

    void removeArticleFromCart(Integer customerId, Integer articleId);

    void addArticleToCart(Integer customerId, Integer articleId);

    void decrementArticleQuantityInCart(Integer customerId, Integer articleId);

    boolean checkOutCart(Integer customerId);

    Set<Orderr> getOrdersForCustomer(Integer customerId);

    Cart getCartForCustomer(Integer customerId);

}
