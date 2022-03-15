package de.leuphana.va.onlineshop.order.component.behaviour;

import de.leuphana.va.onlineshop.order.component.structure.Orderr;

import java.util.Set;

public interface OrderComponentService {

    Set<Orderr> getOrdersForCustomer(int customerId);

    Set<Orderr> getAllOrders();

    Orderr getOrder(int orderId);

    boolean createOrder(Orderr order);

    boolean deleteOrder(int orderId);
}
