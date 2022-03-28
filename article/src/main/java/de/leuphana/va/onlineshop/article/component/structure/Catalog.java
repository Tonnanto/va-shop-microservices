package de.leuphana.va.onlineshop.article.component.structure;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Catalog {

    @Id
    private String catalogId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Set<Article> articles;

    public Catalog() {
        this.articles = new HashSet<>();
        this.catalogId = UUID.randomUUID().toString();
    }

    public Catalog(String id) {
        this.catalogId = id;
    }

    /**
     * @return a demo Catalog to populate the database with.
     */
    static public Set<Article> getInitialArticles() {
        Set<Article> articles = new HashSet<>();

        Article article1 = new Article();
        article1.setName("Toller Artikel");
        article1.setPrice(420.69f);

        Book article2 = new Book();
        article2.setName("Tolles Buch");
        article2.setPrice(9.99f);
        article2.setManufacturer("Springer Verlag");
        article2.setAuthor("Anton Stamme");
        article2.setBookCategory(BookCategory.CRIME);

        CD article3 = new CD();
        article3.setName("Tolle CD");
        article3.setPrice(420.69f);
        article3.setArtist("Ranji & Berg");
        article3.setManufacturer("VooV");

        Book article4 = new Book();
        article4.setName("Entwickeln von Web-Anwendungen");
        article4.setPrice(23.0f);
        article4.setBookCategory(BookCategory.POPULAR_SCIENCE);

        Book article5 = new Book();
        article5.setName("Java in a nutshell");
        article5.setPrice(10.5f);
        article5.setBookCategory(BookCategory.POPULAR_SCIENCE);

        Book article6 = new Book();
        article6.setName("Servlets");
        article6.setPrice(16.5f);
        article6.setBookCategory(BookCategory.POPULAR_SCIENCE);

        return new HashSet<>(Arrays.asList(article1, article2, article3, article4, article5, article6));
    }

    public String getCatalogId() {
        return catalogId;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

    public void addArticles(Set<Article> articles) {
        this.articles.addAll(articles);
    }

    public Article getArticle(int articleId) {
        Article foundArticle = null;

        for (Article article : articles) {
            if (article.getArticleId() == articleId) {
                foundArticle = article;
                break;
            }
        }

        return foundArticle;
    }
}
