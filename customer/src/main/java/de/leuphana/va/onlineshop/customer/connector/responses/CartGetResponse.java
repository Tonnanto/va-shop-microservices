package de.leuphana.va.onlineshop.customer.connector.responses;

import de.leuphana.va.onlineshop.customer.component.structure.Cart;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public record CartGetResponse(
        Cart cart
) {}
