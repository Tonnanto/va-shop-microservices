package de.leuphana.va.onlineshop.article.connector.responses;

import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;

import java.util.Set;

public record AllArticlesGetResponse(
   Set<ArticleDto> articles
) {}
