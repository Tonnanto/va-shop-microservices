package de.leuphana.va.onlineshop.shop.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.requests.ArticleWriteRequest;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleGetResponse;
import de.leuphana.va.onlineshop.article.component.structure.responses.ArticleWriteResponse;
import de.leuphana.va.onlineshop.shop.connector.ApiGatewayRestConnectorRequester;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final ApiGatewayRestConnectorRequester apiGatewayRestConnectorRequester;

    public SupplierServiceImpl(ApiGatewayRestConnectorRequester apiGatewayRestConnectorRequester) {
        this.apiGatewayRestConnectorRequester = apiGatewayRestConnectorRequester;
    }

    @Override
    public boolean insertArticle(Article article) {
        ArticleWriteRequest requestBody = new ArticleWriteRequest(article);
        ArticleWriteResponse responseBody = apiGatewayRestConnectorRequester.createArticle(requestBody).getBody();
        if (responseBody == null) return false;
        return responseBody.success();
    }

    @Override
    public boolean updateArticle(Article article) {
        ArticleWriteRequest requestBody = new ArticleWriteRequest(article);
        ArticleWriteResponse responseBody = apiGatewayRestConnectorRequester.updateArticle(requestBody).getBody();
        if (responseBody == null) return false;
        return responseBody.success();
    }

    @Override
    public boolean removeArticle(int articleId) {
        ArticleWriteResponse responseBody = apiGatewayRestConnectorRequester.deleteArticle(articleId).getBody();
        if (responseBody == null) return false;
        return responseBody.success();
    }

    @Override
    public Article getArticle(int articleId) {
        ArticleGetResponse responseBody = apiGatewayRestConnectorRequester.getArticle(articleId).getBody();
        if (responseBody == null) return null;
        return responseBody.article();
    }
}
