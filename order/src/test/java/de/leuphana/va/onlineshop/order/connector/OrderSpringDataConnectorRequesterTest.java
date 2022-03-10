package de.leuphana.va.onlineshop.order.connector;

import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import de.leuphana.va.onlineshop.order.component.structure.OrderPosition;
import de.leuphana.va.onlineshop.order.configuration.OrderConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = { OrderConfiguration.class })
public class OrderSpringDataConnectorRequesterTest {

    private static OrderSpringDataConnectorRequester orderDataConnectorRequester;
    private static Orderr order;

    @BeforeAll
    static void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(OrderConfiguration.class);
        orderDataConnectorRequester = (OrderSpringDataConnectorRequester) applicationContext.getBean("orderSpringDataConnectorRequester");

        OrderPosition orderPosition1 = new OrderPosition();
        orderPosition1.setArticleId(1);
        orderPosition1.setArticleQuantity(3);

        OrderPosition orderPosition2 = new OrderPosition();
        orderPosition2.setArticleId(1);
        orderPosition2.setArticleQuantity(2);

        OrderPosition orderPosition3 = new OrderPosition();
        orderPosition3.setArticleId(3);
        orderPosition3.setArticleQuantity(2);

        order = new Orderr();
        order.setCustomerId(1234);
        order.addOrderPosition(orderPosition1);
        order.addOrderPosition(orderPosition2);
        order.addOrderPosition(orderPosition3);
    }

    @org.junit.jupiter.api.Order(1)
    @Test
    void canOrderBeCreated() {
        orderDataConnectorRequester.saveOrder(order);
        Assertions.assertNotNull(orderDataConnectorRequester.findOrderById(order.getOrderId()));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void canOrdersForCustomerBeGotten() {
        Assertions.assertEquals(1, orderDataConnectorRequester.findAllOrdersForCustomer(1234).size());
        Assertions.assertEquals(3, orderDataConnectorRequester.findAllOrdersForCustomer(1234).iterator().next().getNumberOfArticles());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void canOrderBeRemoved() {
        orderDataConnectorRequester.deleteOrder(order.getOrderId());
        Assertions.assertEquals(0, orderDataConnectorRequester.findAllOrdersForCustomer(1234).size());
    }

}
