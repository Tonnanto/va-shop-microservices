package de.leuphana.va.onlineshop.order.component.behaviour;

import de.leuphana.va.onlineshop.order.component.structure.Order;

import java.util.List;

public interface OrderComponentService {

    List<Order> getOrdersForCustomer(int customerId);

}
