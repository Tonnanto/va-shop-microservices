package de.leuphana.va.onlineshop.order.component.behaviour;

import de.leuphana.va.onlineshop.order.component.structure.Orderr;

import java.util.List;

public interface OrderComponentService {

    List<Orderr> getOrdersForCustomer(int customerId);

}
