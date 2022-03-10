package de.leuphana.va.onlineshop.customer.connector;

import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.component.structure.CartItem;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.customer.configuration.CustomerConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = { CustomerConfiguration.class })
public class CustomerSpringDataConnectorRequesterTest {

    private static CustomerSpringDataConnectorRequester customerDataConnectorRequester;
    private static Customer customer;
    private static Cart cart;

    @BeforeAll
    static void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerConfiguration.class);
        customerDataConnectorRequester = (CustomerSpringDataConnectorRequester) applicationContext.getBean("customerSpringDataConnectorRequester");

        cart = new Cart();
        customer = new Customer(cart);
        customer.setName("Anton Stamme");
        customer.setAddress("Koppelstieg 38c, 21109, Hamburg");
    }

    @Order(1)
    @Test
    void canCustomerWithCartBeCreated() {
        customerDataConnectorRequester.saveCustomer(customer);
        Assertions.assertNotNull(customerDataConnectorRequester.findCustomerById(customer.getCustomerId()));
    }

    @Order(2)
    @Test
    void canCartForCustomerBeGotten() {
        Cart cartForCustomer = customerDataConnectorRequester.getCartForCustomer(customer.getCustomerId());
        Assertions.assertNotNull(cartForCustomer);
        cart = cartForCustomer;
    }

    @Order(3)
    @Test
    void canArticleBeAddedToCart() {
        customerDataConnectorRequester.addArticleToCart(1, cart.getCartId());
        Assertions.assertNotNull(customerDataConnectorRequester.findCartItemByCartAndArticle(1, cart.getCartId()));
    }

    @Order(4)
    @Test
    void canArticleQuantityBeChanged() {
        customerDataConnectorRequester.updateCartItemQuantity(1, cart.getCartId(), 4);
        CartItem cartItem = customerDataConnectorRequester.findCartItemByCartAndArticle(1, cart.getCartId());
        Assertions.assertNotNull(cartItem);
        Assertions.assertEquals(4, cartItem.getQuantity());
    }

    @Order(5)
    @Test
    void canArticleBeRemovedFromCart() {
        customerDataConnectorRequester.removeArticleFromCart(1, cart.getCartId());
        Assertions.assertNull(customerDataConnectorRequester.findCartItemByCartAndArticle(1, cart.getCartId()));
        Assertions.assertEquals(0, customerDataConnectorRequester.getCartForCustomer(customer.getCustomerId()).getNumberOfArticles());
    }

    @Order(6)
    @Test
    void canCustomerWithCartBeDeleted() {
        customerDataConnectorRequester.deleteCustomerWithCart(customer.getCustomerId());
        Assertions.assertNull(customerDataConnectorRequester.findCustomerById(customer.getCustomerId()));
    }

}
