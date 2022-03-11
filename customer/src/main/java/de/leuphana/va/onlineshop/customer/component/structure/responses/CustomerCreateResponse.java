package de.leuphana.va.onlineshop.customer.component.structure.responses;

import de.leuphana.va.onlineshop.customer.component.structure.Customer;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public record CustomerCreateResponse(
        Customer customer
) {}
