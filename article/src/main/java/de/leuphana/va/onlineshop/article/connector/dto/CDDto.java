package de.leuphana.va.onlineshop.article.connector.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.va.onlineshop.article.component.structure.Article;
import de.leuphana.va.onlineshop.article.component.structure.CD;
import de.leuphana.va.onlineshop.article.connector.dto.ArticleDto;

import javax.persistence.Entity;
import java.io.IOException;
import java.io.StringWriter;

@Entity
public class CDDto extends ArticleDto {

	private String artist;

	public CDDto() { super(); }

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public CD toArticle() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, this);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(writer.toString(), CD.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}