package de.leuphana.va.onlineshop.customer.component.behaviour;

import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.component.structure.CartItem;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.customer.connector.CustomerSpringDataConnectorRequester;
import org.springframework.stereotype.Service;

@Service
public class CustomerComponentServiceImpl implements CustomerComponentService {

    private final CustomerSpringDataConnectorRequester dataConnector;

    public CustomerComponentServiceImpl(CustomerSpringDataConnectorRequester dataConnector) {
        this.dataConnector = dataConnector;
    }

    @Override
    public Customer getCustomer(Integer customerId) {
        return dataConnector.findCustomerById(customerId);
    }

    @Override
    public Customer createCustomerWithCart(String name, String address) {
        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setAddress(address);
        dataConnector.saveCustomer(newCustomer);
        return newCustomer;
    }

    @Override
    public void removeArticleFromCart(Integer customerId, int articleId) {
        dataConnector.removeArticleFromCart(articleId, customerId);
    }

    @Override
    public void addArticleToCart(Integer customerId, Integer articleId) {
        dataConnector.addArticleToCart(articleId, customerId);
    }

    @Override
    public void decrementArticleQuantityInCart(Integer customerId, Integer articleId) {
        CartItem cartItem = dataConnector.findCartItemByCustomerAndArticle(articleId, customerId);
        if (cartItem == null) return;
        dataConnector.updateCartItemQuantity(articleId, customerId, cartItem.getQuantity() - 1);
    }

    @Override
    public Cart getCartForCustomer(Integer customerId) {
        return dataConnector.getCartForCustomer(customerId);
    }

}
