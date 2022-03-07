package de.leuphana.va.onlineshop.customer.component.behaviour;

import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.order.component.structure.Order;

public interface CustomerComponentService {

    Integer createCustomerWithCart();

    void removeArticleFromCart(Integer customerId, int articleId);

    void addArticleToCart(Integer customerId, Integer articleId);

    void decrementArticleQuantityInCart(Integer customerId, Integer articleId);

    Cart getCartForCustomer(Integer customerId);

    Order checkOutCart(int customerId);

}
