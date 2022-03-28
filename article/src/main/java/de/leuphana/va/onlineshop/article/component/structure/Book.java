package de.leuphana.va.onlineshop.article.component.structure;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Book extends Article {

    private String author;

    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;

    public Book() {
        super();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

}