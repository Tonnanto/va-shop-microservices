package de.leuphana.va.onlineshop.article.component.behaviour;

import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.Book;
import de.leuphana.va.onlineshop.article.component.structure.BookCategory;
import de.leuphana.va.onlineshop.article.component.structure.CD;
import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;
import de.leuphana.va.onlineshop.article.connector.dto.BookDto;
import de.leuphana.va.onlineshop.article.connector.dto.CDDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArticleComponentServiceTest {

    @Test
    public void canArticleDtoBeMapped() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setArticleId(1234);
        articleDto.setName("Name 123");
        articleDto.setManufacturer("Manufacturer 123");
        articleDto.setPrice(420.69f);

        Article article = articleDto.toArticle();

        Assertions.assertEquals(articleDto.getArticleId(), article.getArticleId());
        Assertions.assertEquals(articleDto.getManufacturer(), article.getManufacturer());
        Assertions.assertEquals(articleDto.getName(), article.getName());
        Assertions.assertEquals(articleDto.getPrice(), article.getPrice());
    }

    @Test
    public void canBookDtoBeMapped() {
        BookDto bookDto = new BookDto();
        bookDto.setArticleId(125);
        bookDto.setName("Book 123");
        bookDto.setManufacturer("Manufacturer 123");
        bookDto.setPrice(123.45f);
        bookDto.setAuthor("Anton Stamme");
        bookDto.setBookCategory(BookCategory.CRIME);

        Book book = bookDto.toArticle();

        Assertions.assertEquals(bookDto.getArticleId(), book.getArticleId());
        Assertions.assertEquals(bookDto.getManufacturer(), book.getManufacturer());
        Assertions.assertEquals(bookDto.getName(), book.getName());
        Assertions.assertEquals(bookDto.getPrice(), book.getPrice());
        Assertions.assertEquals(bookDto.getAuthor(), book.getAuthor());
        Assertions.assertEquals(bookDto.getBookCategory(), book.getBookCategory());
    }

    @Test
    public void canCDDtoBeMapped() {
        CDDto cdDto = new CDDto();
        cdDto.setArticleId(1234);
        cdDto.setName("Name 123");
        cdDto.setManufacturer("Manufacturer 123");
        cdDto.setPrice(420.69f);
        cdDto.setArtist("Ranji & Berg");

        CD cd = cdDto.toArticle();

        Assertions.assertEquals(cdDto.getArticleId(), cd.getArticleId());
        Assertions.assertEquals(cdDto.getManufacturer(), cd.getManufacturer());
        Assertions.assertEquals(cdDto.getName(), cd.getName());
        Assertions.assertEquals(cdDto.getPrice(), cd.getPrice());
        Assertions.assertEquals(cdDto.getArtist(), cd.getArtist());
    }

    @Test
    public void canArticleBeMapped() {
        Article article = new Article();
        article.setArticleId(1234);
        article.setName("Name 123");
        article.setManufacturer("Manufacturer 123");
        article.setPrice(420.69f);

        ArticleDto articleDto = ArticleDto.fromArticle(article);

        Assertions.assertEquals(article.getArticleId(), articleDto.getArticleId());
        Assertions.assertEquals(article.getManufacturer(), articleDto.getManufacturer());
        Assertions.assertEquals(article.getName(), articleDto.getName());
        Assertions.assertEquals(article.getPrice(), articleDto.getPrice());
    }

    @Test
    public void canBookBeMapped() {
        Book book = new Book();
        book.setArticleId(125);
        book.setName("Book 123");
        book.setManufacturer("Manufacturer 123");
        book.setPrice(123.45f);
        book.setAuthor("Anton Stamme");
        book.setBookCategory(BookCategory.CRIME);

        BookDto bookDto = BookDto.fromArticle(book);

        Assertions.assertEquals(book.getArticleId(), bookDto.getArticleId());
        Assertions.assertEquals(book.getManufacturer(), bookDto.getManufacturer());
        Assertions.assertEquals(book.getName(), bookDto.getName());
        Assertions.assertEquals(book.getPrice(), bookDto.getPrice());
        Assertions.assertEquals(book.getAuthor(), bookDto.getAuthor());
        Assertions.assertEquals(book.getBookCategory(), bookDto.getBookCategory());
    }
}
