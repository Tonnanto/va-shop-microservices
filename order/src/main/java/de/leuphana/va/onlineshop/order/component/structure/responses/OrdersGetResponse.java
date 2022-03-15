package de.leuphana.va.onlineshop.order.component.structure.responses;

import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@ResponseBody
public record OrdersGetResponse(
   Set<Orderr> orders
) {}
