package com.audiolibrary.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Artist {

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("artist")
    private Set<Album> albums;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private Long id;

    @Column(name = "Name")
    private String name;

    public Artist() {

    }

    public Artist(Set<Album> albums, Long id, String name) {
        this.albums = albums;
        this.id = id;
        this.name = name;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Album> getAlbums() {
        return albums;
    }


}


