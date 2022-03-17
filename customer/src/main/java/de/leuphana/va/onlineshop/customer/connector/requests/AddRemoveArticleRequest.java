package de.leuphana.va.onlineshop.customer.connector.requests;

public record AddRemoveArticleRequest(
   int customerId,
   int articleId
) {}
