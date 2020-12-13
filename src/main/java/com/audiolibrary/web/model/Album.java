package com.audiolibrary.web.model;


import javax.persistence.*;

@Entity
public class Album {

    @ManyToOne
    @JoinColumn(name = "ArtistId")
    private Artist artist;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private Long id;

    @Column(name = "Title")
    private String title;

    public Album() {

    }

    public Album(Artist artist, String title, Long id) {
        this.artist = artist;
        this.title = title;
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}


