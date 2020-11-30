package com.audiolibrary.web.controller;


import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PostLoad;


import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.AlbumRepository;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {


    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;


    //AJOUT

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Album ajoutAlbum(@RequestBody Album album) {

        return albumRepository.save(album);
    }





    //DELETE ALBUM
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void  deletAl(@PathVariable("id") Long id){
    if(!albumRepository.existsById(id)){
        throw  new EntityNotFoundException("Alub " + id +" n'exist pas  ");
    }
    albumRepository.deleteById(id);
    }




}