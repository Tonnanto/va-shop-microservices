package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;

import java.util.Set;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public Integer createCustomerWithCart() {
        return null;
    }

    @Override
    public Article getArticle(int articleId) {
        return null;
    }

    @Override
    public Set<Article> getArticles() {
        return null;
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
