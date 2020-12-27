package com.audiolibrary.web.controllerThymeleaf;


import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.AlbumRepository;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/artists")
public class ArtisteController {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;


//    GET PAR ID
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getArtistById(@PathVariable Long id, final ModelMap model){

        Optional<Artist> artistOptional = artistRepository.findById(id);
        if(artistOptional.isEmpty()){
            throw new EntityNotFoundException("Artist d'identifiant " + id + " n'a pas été trouvé !");
        }

        model.put("artiste", artistOptional.get());


        return "detailArtist";
    }


//    SEARCH
@RequestMapping(method = RequestMethod.GET , params = "name")
public String Search(final ModelMap model, @RequestParam String name,
                     @RequestParam(defaultValue = "0") Integer page,
                     @RequestParam(defaultValue = "10") Integer size,
                     @RequestParam(defaultValue = "name") String sortProperty,
                     @RequestParam(defaultValue = "ASC") String sortDirection) {

    Page<Artist> artists = artistRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty));
    if(!artists.isEmpty()) {
        model.put("artists", artists);
        model.put("start", (page) * size + 1);
        model.put("end", (page) * size + artists.getNumberOfElements());
        model.put("ArtsitTotolfinde", artists.getTotalElements());
        model.put("previousPage", page - 1);
        model.put("currentPage", page);
        model.put("nextPage", page + 1);
        model.put("isLastPage", artists.isLast());
    } else {
        model.put("start", 0);
        model.put("end", 0);
        model.put("totalArtist", 0);
        model.put("previousPage", 0);
        model.put("currentPage", 0);
        model.put("nextPage", 0);
        model.put("isLastPage", true);

    }
    return "listeArtists";
}




    @RequestMapping(method = RequestMethod.GET, value = "")
    public String ListeArtist(final ModelMap model,
                                @RequestParam Integer page,
                                @RequestParam Integer size,
                                @RequestParam String sortProperty,
                                @RequestParam String sortDirection) {
        Page<Artist> artists = artistRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty));
        if(!artists.isEmpty()) {
            model.put("artists", artists);
            model.put("start", (page) * size + 1);
            model.put("end", (page) * size + artists.getNumberOfElements());
            model.put("totalArtist", artists.getTotalElements());
            model.put("previousPage", page - 1);
            model.put("currentPage", page);
            model.put("nextPage", page + 1);
            model.put("isLastPage", artists.isLast());
        } else {

        }

        return "listeArtists";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String CreateArtist(Artist artist, final ModelMap model) {


            artistRepository.save(artist);


            model.put("artiste", artist);



            return "detailArtist";

        }






    //AJOUT ALBUM A L'artist par id de l'artiste

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/album")
    public RedirectView createAlbum(@PathVariable Long id, Album album) {
        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isEmpty()) {

            return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
        }

        if(!album.getTitle().isEmpty()) {
            album.setArtist(artist.get());
            albumRepository.save(album);

        }

        return new RedirectView("/artists/"+artist.get().getId());
    }




        //DELETE ALBUMS
    @RequestMapping(method = RequestMethod.GET, value = "/{id_ar}/album/delete/{id_al}")
    public RedirectView deleteAlbum(@PathVariable Long id_ar, @PathVariable Long id_al) {
        Optional<Artist> artistOptional = artistRepository.findById(id_ar);
        Optional<Album>  albumOptional = albumRepository.findById(id_al);
        if(artistOptional.isEmpty()) {

            return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
        }

        albumRepository.deleteById(id_al);


        return new RedirectView("/artists/"+artistOptional.get().getId());
    }




    @RequestMapping(method = RequestMethod.GET, value = "/{id}/delete")

    public RedirectView Delete(@PathVariable Long id) {
        Optional<Artist> artistOptional = artistRepository.findById(id);



        Artist artist = artistOptional.get();


        Set<Album> albums = artist.getAlbums();
        for (Album album : albums){
            albumRepository.delete(album);

        }

        artistRepository.deleteById(id);

        return new RedirectView("/");


    }























}
