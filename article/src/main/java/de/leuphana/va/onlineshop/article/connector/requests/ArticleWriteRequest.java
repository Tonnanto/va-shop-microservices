package de.leuphana.va.onlineshop.article.connector.requests;

import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;

public record ArticleWriteRequest(
   ArticleDto article
) {}
