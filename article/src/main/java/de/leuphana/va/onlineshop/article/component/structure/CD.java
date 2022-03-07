package de.leuphana.va.onlineshop.article.component.structure;

public class CD extends Article {

	private String artist;

	public CD(int articleId) {
		super(articleId);
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

}