package de.leuphana.va.onlineshop.order.component.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orderr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private int customerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderPosition> orderPositions;

    public Orderr() {
        orderPositions = new HashSet<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Set<OrderPosition> getOrderPositions() {
        return orderPositions;
    }

    public void addOrderPosition(OrderPosition orderPosition) {
        orderPositions.add(orderPosition);
    }

    public int getNumberOfArticles() {
        return orderPositions.size();
    }

}