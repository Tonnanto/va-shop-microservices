package de.leuphana.va.onlineshop.customer.component.structure;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;
    private String name;
    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cart cart;


    public Customer() {
        this.cart = new Cart();
    }

    public Customer(Cart cart) {
        this.cart = cart;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Cart getCart() {
        return cart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}