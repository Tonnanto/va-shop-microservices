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

        Book article2 = new Book();
        article2.setName("Entwickeln von Web-Anwendungen");
        article2.setPrice(23.0f);
        article2.setBookCategory(BookCategory.POPULAR_SCIENCE);

        Book article3 = new Book();
        article3.setName("Java in a nutshell");
        article3.setPrice(10.5f);
        article3.setBookCategory(BookCategory.POPULAR_SCIENCE);

        Book article4 = new Book();
        article4.setName("Servlets");
        article4.setPrice(16.5f);
        article4.setBookCategory(BookCategory.POPULAR_SCIENCE);


        Book article5 = new Book();
        article5.setName("Clean Code: A Handbook of Agile Software Craftsmanship");
        article5.setAuthor("Robert C. \"Uncle Bob\" Martin");
        article5.setPrice(41.59f);
        article5.setBookCategory(BookCategory.SOFTWARE_TESTING);

        Book article6 = new Book();
        article6.setName("The Pragmatic Programmer: From Journeyman to Master");
        article6.setAuthor("Andrew Hunt, Dave Thomas");
        article6.setPrice(145.78f);
        article6.setBookCategory(BookCategory.COMPUTER_SCIENCE);

        Book article7 = new Book();
        article7.setName("Code Complete: A Practical Handbook of Software Construction");
        article7.setAuthor("Steve McConnell");
        article7.setPrice(32.49f);
        article7.setBookCategory(BookCategory.NETWORK_AND_CLOUD);

        Book article8 = new Book();
        article8.setName("Design Patterns: Elements of Reusable Object-Oriented Software");
        article8.setAuthor("Erich Gamma, Richard Helm, and Ralph Johnson");
        article8.setPrice(39.99f);
        article8.setBookCategory(BookCategory.COMPUTER_SCIENCE);

        Book article9 = new Book();
        article9.setName("Refactoring: Improving the Design of Existing Code");
        article9.setAuthor("Martin Fowler");
        article9.setPrice(19.99f);
        article9.setBookCategory(BookCategory.NETWORK_AND_CLOUD);

        Book article10 = new Book();
        article10.setName("The Mythical Man-Month: Essays on Software Engineering");
        article10.setAuthor("Frederick P. Brooks");
        article10.setPrice(16.88f);
        article10.setBookCategory(BookCategory.PROGRAMMING);

        Book article11 = new Book();
        article11.setName("Working Effectively with Legacy Code");
        article11.setAuthor("Michael Feathers");
        article11.setPrice(45.97f);
        article11.setBookCategory(BookCategory.PROGRAMMING);



        CD article12 = new CD();
        article12.setName("Tolle CD");
        article12.setPrice(420.69f);
        article12.setArtist("Ranji & Berg");
        article12.setManufacturer("VooV");

        return new HashSet<>(Arrays.asList(article2, article3, article4, article5, article6, article7, article8, article9, article10, article11, article12));
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
