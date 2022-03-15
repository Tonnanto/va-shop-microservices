package de.leuphana.va.onlineshop.article.component.structure.responses;

import de.leuphana.va.onlineshop.article.component.structure.Article;

import java.util.Set;

public record AllArticlesGetResponse(
   Set<Article> articles
) {}
