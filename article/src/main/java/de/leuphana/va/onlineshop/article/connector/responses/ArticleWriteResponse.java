package de.leuphana.va.onlineshop.article.connector.responses;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public record ArticleWriteResponse(
   boolean success
) {}
