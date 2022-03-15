package de.leuphana.va.onlineshop.order.component.behaviour;

import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import de.leuphana.va.onlineshop.order.connector.OrderSpringDataConnectorRequester;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderComponentServiceImpl implements OrderComponentService {

    private final OrderSpringDataConnectorRequester dataConnector;

    public OrderComponentServiceImpl(OrderSpringDataConnectorRequester dataConnector) {
        this.dataConnector = dataConnector;
    }

    @Override
    public Set<Orderr> getOrdersForCustomer(int customerId) {
        return dataConnector.findAllOrdersForCustomer(customerId);
    }

    @Override
    public Set<Orderr> getAllOrders() {
        return dataConnector.findAllOrders();
    }

    @Override
    public Orderr getOrder(int orderId) {
        return dataConnector.findOrderById(orderId);
    }

    @Override
    public boolean createOrder(Orderr order) {
        dataConnector.saveOrder(order);
        return true; // TODO: creation success?
    }

    @Override
    public boolean deleteOrder(int orderId) {
        dataConnector.deleteOrder(orderId);
        return true; // TODO: deletion success?
    }
}
