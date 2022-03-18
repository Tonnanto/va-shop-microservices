package de.leuphana.va.onlineshop.article.connector.responses;

import de.leuphana.va.onlineshop.article.connector.dto.CatalogDto;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public record CatalogGetResponse(
   CatalogDto catalog
) {}
