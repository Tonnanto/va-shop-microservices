package de.leuphana.va.onlineshop.customer.connector;

import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.component.structure.CartItem;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomerSpringDataConnectorRequester {

    @Autowired
    private SessionFactory sessionFactory;

    public CustomerSpringDataConnectorRequester() {
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for Customer
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void saveCustomer(Customer customer) {
        getCurrentSession().save(customer);
    }

    public Customer findCustomerById(int customerId) {
        return getCurrentSession().find(Customer.class, customerId);
    }

    public void deleteCustomerWithCart(int customerId) {
        Customer customerToDelete = findCustomerById(customerId);
        getCurrentSession().delete(customerToDelete);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for Cart
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Cart getCartForCustomer(int customerId) {
        Customer foundCustomer = findCustomerById(customerId);
        if (foundCustomer == null) return null;
        return foundCustomer.getCart();
    }

    public Cart findCartById(int cartId) {
        return getCurrentSession().find(Cart.class, cartId);
    }

    public void addArticleToCart(int articleId, int customerId) {
        Cart foundCart = getCartForCustomer(customerId);
        if (foundCart == null) return;
        foundCart.addCartItem(articleId);
        getCurrentSession().save(foundCart);
    }

    public void removeArticleFromCart(int articleId, int customerId) {
        Cart foundCart = getCartForCustomer(customerId);
        if (foundCart == null) return;
        foundCart.deleteCartItem(articleId);
        getCurrentSession().save(foundCart);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for CartItem
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public CartItem findCartItemById(int cartItemId) {
        return getCurrentSession().find(CartItem.class, cartItemId);
    }

    public CartItem findCartItemByCustomerAndArticle(int articleId, int customerId) {
        Cart foundCart = getCartForCustomer(customerId);
        if (foundCart == null) return null;
        return foundCart.getCartItemsMap().get(articleId);
    }

    public void updateCartItemQuantity(int articleId, int customerId, int quantity) {
        CartItem foundCartItem = findCartItemByCustomerAndArticle(articleId, customerId);
        if (foundCartItem == null) return;
        if (quantity <= 0) removeArticleFromCart(articleId, customerId);
        foundCartItem.setQuantity(quantity);

        getCurrentSession().save(foundCartItem);
    }
}
