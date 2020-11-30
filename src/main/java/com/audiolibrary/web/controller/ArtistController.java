package com.audiolibrary.web.controller;


import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.AlbumRepository;
import com.audiolibrary.web.repository.ArtistRepository;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist getArtsite(@PathVariable(value = "id") Long id) {
        Optional<Artist> artist = artistRepository.findById(id);

        return artist.get();
    }


    //SERACH
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"name"})
    Page<Artist>
    serchArtist(
            @RequestParam(value = "name") String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection) {
        if (page < 0) {
            //400
            throw new IllegalArgumentException("La param du page doit etre positif ou nul");
        }
        if (size <= 0 || size > 50) {
            throw new IllegalArgumentException("Laparam size doit etre compris entre 0 et 50");
        }
        if (!"ASC".equalsIgnoreCase(sortDirection) &&
                !"DESC".equalsIgnoreCase(sortDirection)) {
            throw new IllegalArgumentException("Le paramètre sortDirection doit valoir ASC ou DESC");
        }

        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> listeArtsites = artistRepository.findByNameContainingIgnoreCase(name, pageRequest);
        return listeArtsites;
    }


    //Liste des artiste page
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Page<Artist>
    allartiste(

            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection) {

        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> listallartist = artistRepository.findAll(pageRequest);
        return listallartist;
    }


    //AJOUT ARTISTE
    @RequestMapping(method = RequestMethod.POST,

            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Artist creatArtiste(@RequestBody Artist artist) {
        if (artistRepository.findByName(artist.getName()) != null) {
            throw new EntityNotFoundException("Artiste de exsite deha " + artist.getName());
        }
        return artistRepository.save(artist);
    }


    //MODIF PUT

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist updateArtist(@RequestBody Artist artist) {
        return artistRepository.save(artist);

    }


    //DELETE


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")

    public void deleteArtiste(@PathVariable Long id) {

            Optional<Album> albumOptional = albumRepository.findAlbumByArtist(id);

            Album album = albumOptional.get();

            album.getArtist();


            artistRepository.deleteById(id);
            albumRepository.delete(album);

        }


}



