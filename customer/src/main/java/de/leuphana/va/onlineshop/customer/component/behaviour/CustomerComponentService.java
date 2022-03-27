package de.leuphana.va.onlineshop.customer.component.behaviour;

import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;

public interface CustomerComponentService {

    Customer createCustomerWithCart(String name, String address);

    Customer getCustomer(Integer customerId);

    void removeArticleFromCart(Integer customerId, int articleId);

    void addArticleToCart(Integer customerId, Integer articleId);

    void decrementArticleQuantityInCart(Integer customerId, Integer articleId);

    Cart getCartForCustomer(Integer customerId);

//    Orderr checkOutCart(int customerId);

}
