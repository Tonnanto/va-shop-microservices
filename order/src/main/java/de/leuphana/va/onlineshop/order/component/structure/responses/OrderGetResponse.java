package de.leuphana.va.onlineshop.order.component.structure.responses;

import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public record OrderGetResponse(
        Orderr order
) {
}
