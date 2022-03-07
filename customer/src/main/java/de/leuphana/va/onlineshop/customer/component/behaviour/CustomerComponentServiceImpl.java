package de.leuphana.va.onlineshop.customer.component.behaviour;

import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.order.component.structure.Order;

public class CustomerComponentServiceImpl implements CustomerComponentService {
    @Override
    public Integer createCustomerWithCart() {
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
    public Cart getCartForCustomer(Integer customerId) {
        return null;
    }

    @Override
    public Order checkOutCart(int customerId) {
        return null;
    }
}
