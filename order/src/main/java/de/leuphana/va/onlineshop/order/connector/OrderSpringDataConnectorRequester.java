package de.leuphana.va.onlineshop.order.connector;

import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class OrderSpringDataConnectorRequester {

    public OrderSpringDataConnectorRequester() {
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for Order
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void saveOrder(Orderr order) {
        getCurrentSession().save(order);
    }

    public Orderr findOrderById(int orderId) {
        return getCurrentSession().find(Orderr.class, orderId);
    }

    public Set<Orderr> findAllOrdersForCustomer(int customerId) {
        Set<Orderr> orders = new HashSet<>();

        String queryString = "SELECT * FROM Orderr WHERE customerId = " + customerId;

        NativeQuery<Orderr> query = getCurrentSession().createNativeQuery(queryString, Orderr.class);
        List<?> results = query.getResultList();

        for (Object result: results) {
            if (result instanceof Orderr order) {
                orders.add(order);
            }
        }

        return orders;
    }

    public Set<Orderr> findAllOrders() {
        Set<Orderr> orders = new HashSet<>();

        String queryString = "SELECT * FROM Orderr";

        NativeQuery<Orderr> query = getCurrentSession().createNativeQuery(queryString, Orderr.class);
        List<?> results = query.getResultList();

        for (Object result: results) {
            if (result instanceof Orderr order) {
                orders.add(order);
            }
        }

        return orders;
    }

    public void deleteOrder(int orderId) {
        Orderr orderToDelete = findOrderById(orderId);
        getCurrentSession().delete(orderToDelete);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Operations for OrderrPosition
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



}
