package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;

import java.util.Set;

public interface CustomerService {

    Integer createCustomerWithCart();

    Article getArticle(int articleId);

    Set<Article> getArticles();

    void removeArticleFromCart(Integer customerId, int articleId);

    void addArticleToCart(Integer customerId, Integer articleId);

    void decrementArticleQuantityInCart(Integer customerId, Integer articleId);

    Orderr checkOutCart(int customerId);

    Cart getCartForCustomer(Integer customerId);

}
