package de.leuphana.va.onlineshop.article.component.structure.responses;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public record ArticleGetResponse(
        Article article
) {
}
