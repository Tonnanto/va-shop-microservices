package de.leuphana.va.onlineshop.customer.component.structure.requests;

public record AddRemoveArticleRequest(
   int customerId,
   int articleId
) {}
