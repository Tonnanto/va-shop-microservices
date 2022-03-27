package de.leuphana.va.onlineshop.customer.connector;

import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import de.leuphana.va.onlineshop.customer.component.structure.CartItem;
import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import de.leuphana.va.onlineshop.customer.configuration.CustomerConfiguration;
import de.leuphana.va.onlineshop.customer.configuration.CustomerTestConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = { CustomerTestConfiguration.class })
@ActiveProfiles("test")
public class CustomerSpringDataConnectorRequesterTest {

    private static CustomerSpringDataConnectorRequester customerDataConnectorRequester;
    private static Customer customer;
    private static Cart cart;

    @BeforeAll
    static void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CustomerTestConfiguration.class);
        customerDataConnectorRequester = (CustomerSpringDataConnectorRequester) applicationContext.getBean("testCustomerSpringDataConnectorRequester");

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
    void canArticlesBeAddedToCart() {
        customerDataConnectorRequester.addArticleToCart(1, customer.getCustomerId());
        customerDataConnectorRequester.addArticleToCart(2, customer.getCustomerId());
        customerDataConnectorRequester.addArticleToCart(3, customer.getCustomerId());
        Assertions.assertNotNull(customerDataConnectorRequester.findCartItemByCustomerAndArticle(1, customer.getCustomerId()));
        Assertions.assertNotNull(customerDataConnectorRequester.findCartItemByCustomerAndArticle(2, customer.getCustomerId()));
        Assertions.assertNotNull(customerDataConnectorRequester.findCartItemByCustomerAndArticle(3, customer.getCustomerId()));
        Assertions.assertEquals(3, customerDataConnectorRequester.getCartForCustomer(customer.getCustomerId()).getNumberOfArticles());
    }

    @Order(4)
    @Test
    void canArticleQuantityBeChanged() {
        customerDataConnectorRequester.updateCartItemQuantity(1, customer.getCustomerId(), 4);
        CartItem cartItem = customerDataConnectorRequester.findCartItemByCustomerAndArticle(1, customer.getCustomerId());
        Assertions.assertNotNull(cartItem);
        Assertions.assertEquals(4, cartItem.getQuantity());
    }

    @Order(5)
    @Test
    void canArticleBeRemovedFromCart() {
        customerDataConnectorRequester.removeArticleFromCart(1, customer.getCustomerId());
        customerDataConnectorRequester.removeArticleFromCart(2, customer.getCustomerId());
        Assertions.assertNull(customerDataConnectorRequester.findCartItemByCustomerAndArticle(1, customer.getCustomerId()));
        Assertions.assertNull(customerDataConnectorRequester.findCartItemByCustomerAndArticle(2, customer.getCustomerId()));
        Assertions.assertEquals(1, customerDataConnectorRequester.getCartForCustomer(customer.getCustomerId()).getNumberOfArticles());

        customerDataConnectorRequester.removeArticleFromCart(3, customer.getCustomerId());
        Assertions.assertNull(customerDataConnectorRequester.findCartItemByCustomerAndArticle(3, customer.getCustomerId()));
        Assertions.assertEquals(0, customerDataConnectorRequester.getCartForCustomer(customer.getCustomerId()).getNumberOfArticles());
    }

    @Order(6)
    @Test
    void canCustomerWithCartBeDeleted() {
        customerDataConnectorRequester.deleteCustomerWithCart(customer.getCustomerId());
        Assertions.assertNull(customerDataConnectorRequester.findCustomerById(customer.getCustomerId()));
    }

}
