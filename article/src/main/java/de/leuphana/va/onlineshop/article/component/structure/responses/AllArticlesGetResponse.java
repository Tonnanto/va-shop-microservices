package de.leuphana.va.onlineshop.article.component.structure.responses;

import org.hibernate.mapping.Set;

public record AllArticlesGetResponse(
   Set articles
) {}
