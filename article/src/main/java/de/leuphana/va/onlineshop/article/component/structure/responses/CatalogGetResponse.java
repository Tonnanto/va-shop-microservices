package de.leuphana.va.onlineshop.article.component.structure.responses;

import de.leuphana.va.onlineshop.article.component.structure.Catalog;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public record CatalogGetResponse(
        Catalog catalog
) {
}
