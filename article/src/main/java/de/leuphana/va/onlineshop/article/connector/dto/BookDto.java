package de.leuphana.va.onlineshop.article.connector.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.Book;
import de.leuphana.va.onlineshop.article.component.structure.BookCategory;
import de.leuphana.va.onlineshop.article.component.structure.CD;
import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.IOException;
import java.io.StringWriter;

@Entity
public class BookDto extends ArticleDto {

	private String author;

	@Enumerated(EnumType.STRING)
	private BookCategory bookCategory;

	public BookDto() { super(); }

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

	@Override
	public Book toArticle() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, this);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(writer.toString(), Book.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BookDto fromArticle(Book article) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, article);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
			return mapper.readValue(writer.toString(), BookDto.class);

		} catch (IOException e) {
			System.out.println("VERKACKT");
			e.printStackTrace();
		}
		return null;
	}
}