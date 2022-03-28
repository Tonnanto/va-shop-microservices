package de.leuphana.va.onlineshop.order.component.structure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int positionId;

    private int articleId;
    private int articleQuantity;

    public OrderPosition() {
    }

    public Integer getPositionId() {
        return positionId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getArticleQuantity() {
        return articleQuantity;
    }

    public void setArticleQuantity(int articleQuantity) {
        this.articleQuantity = articleQuantity;
    }

}