package de.leuphana.va.onlineshop.customer.connector.requests;

public record CustomerCreateRequest(
        String name,
        String address
) {
}
