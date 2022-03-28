package de.leuphana.va.onlineshop.article.component.structure;

import javax.persistence.Entity;

@Entity
public class CD extends Article {

    private String artist;

    public CD() {
        super();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}