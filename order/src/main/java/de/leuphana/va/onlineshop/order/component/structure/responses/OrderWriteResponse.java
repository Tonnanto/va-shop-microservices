package de.leuphana.va.onlineshop.order.component.structure.responses;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public record OrderWriteResponse(
   boolean success
) {}
